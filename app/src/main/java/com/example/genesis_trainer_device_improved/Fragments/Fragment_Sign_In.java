package com.example.genesis_trainer_device_improved.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProvider;


import com.example.genesis_trainer_device_improved.Activities.Activity_Second;
import com.example.genesis_trainer_device_improved.Entity.User;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.users_model.IUserViewModel;
import com.example.genesis_trainer_device_improved.ViewModel.users_model.UserViewModel;
import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.retrofit.APIService;
import com.example.genesis_trainer_device_improved.retrofit.ApiUtils;
import com.example.genesis_trainer_device_improved.retrofit.CallbackWrapper;
import com.example.genesis_trainer_device_improved.retrofit.ServerResponse;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class Fragment_Sign_In extends DialogFragment implements LifecycleObserver {
    private static final String TAG = "Fragment_Sign_In";


    private DialogListener dialogListener;
    private EditText email;
    private EditText password;
    private Button logIn;
    private IUserViewModel userViewModel;
    private View view;
    private SharedPreferences sharedPreferences;
    private String imei;
    private CompositeDisposable d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment__sign__in, container, false);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            d= new CompositeDisposable();
        }

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Bundle args=getArguments();
        if (args != null) {
            imei = args.getString("imei");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

         view = inflater.inflate(R.layout.fragment_fragment__sign__in,null);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        email = view.findViewById(R.id.dialog_email);
        password = view.findViewById(R.id.dialog_password);
        logIn = view.findViewById(R.id.button_LogIn);


        loadFromSharedPrefs(email,password);
        builder.setView(view);


        builder.setOnCancelListener(dialog -> {
                dialog.dismiss();
            Log.d(TAG, "onCancel: ");
            });

        logIn.setOnClickListener(v -> {
            loginClicked();
        });

        return builder.create();

    }

    private void loginClicked(){
        String userEmail = email.getText().toString().trim();
        String pass = password.getText().toString();

        if (userEmail.length() > 0 && pass.length() > 0) {
            saveLoginInSharedPrefs( userEmail, pass);
            loadUserByLogin(userEmail,pass);
        }else {
            Toast.makeText(getContext(), "fields cant be empty", Toast.LENGTH_LONG).show();
        }
    }

    private void saveLoginInSharedPrefs(String userEmail,String pass){
        if (getContext() !=null)
            sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(Constants.SHARED_PREF_EMAIL,userEmail);
        edit.putString(Constants.SHARED_PREF_PASSWORD,pass);
        edit.apply();
    }

    private void loadFromSharedPrefs(EditText email,EditText password){
        if (getActivity() !=null && getActivity().getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE) !=null)
            sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREF_KEY, Context.MODE_PRIVATE);
        String rememberedEmail= sharedPreferences.getString(Constants.SHARED_PREF_EMAIL,"");
        String rememberedPassword= sharedPreferences.getString(Constants.SHARED_PREF_PASSWORD,"");

        email.setText(rememberedEmail);
        password.setText(rememberedPassword);
    }



    public void registerObserver(Lifecycle lifecycle){
        lifecycle.addObserver(this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {

            throw new ClassCastException(Activity_Second.class.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
            dialog.dismiss();
        super.onCancel(dialog);
    }



    private void sendDataToServer(String email, String password, String imei){

        APIService apiService = ApiUtils.getAPIService();
        apiService.sendloginInfo(email,password,imei).enqueue(new CallbackWrapper<ServerResponse>((throwable,response,call)->{
            dialogListener.onDialogSignInClick(Fragment_Sign_In.this);
            if ( response!=null && response.isSuccessful() && response.body()!=null){

                         respondToLoginAttempt(response.body().getState());

                Log.d(TAG,"onResponse Call request= "+ call.request().body());
                Log.d(TAG,"onResponse Call request header= "+ call.request().headers().toString());
                Log.d(TAG,"onResponse Response response.body().getState()= "+ response.body().getState());

                Log.d(TAG,"onResponse Response code = "+ response.code());
                Log.d(TAG,"onResponse Response raw= "+ String.valueOf(response.body()));
                Log.d(TAG,"onResponse Response body= "+ response.body());

                    }
            if (throwable!=null){
                Log.d(TAG, "sendDataToServer: error -> "+throwable.getLocalizedMessage());
                throwable.printStackTrace();
                if (getDialog()!=null) getDialog().cancel();
                call.cancel();
            }
        }));
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start() {

        Log.d(TAG, "start: started");
    }

    @Override
    public void onStop() {
       if (!d.isDisposed()) d.dispose();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        sharedPreferences=null;
        getLifecycle().removeObserver(this);
        view=null;
        super.onDestroyView();
    }




    public void loadUserByLogin(String userEmail, String pass) {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED)) {
            d.add( userViewModel.loadUserByEmailAndPassword(userEmail, pass).subscribe(user -> {
                    sendDataToServer(user.getUserEmail(), user.getUserPassword(), imei);
                    Log.d(TAG, "loadUserByLogin: =======");

            }, Throwable::printStackTrace,()->{
                Toast.makeText(getContext(), "User dont exist", Toast.LENGTH_SHORT).show();
            }));
        }
    }


    private void respondToLoginAttempt(String loginMessage){

            switch (loginMessage){
                case "done": dialogListener.onDialogSignInClick(Fragment_Sign_In.this);
                break;
                case "login_short":
                    Toast.makeText(getContext(), "Your User Login length is under 4 characters", Toast.LENGTH_LONG).show();
                    break;
                case "password_short":
                    Toast.makeText(getContext(), "Your Password is under 4 characters", Toast.LENGTH_LONG).show();
                    break;
                case "imei_empty":
                    Toast.makeText(getContext(), "Your phone identifying number is empty", Toast.LENGTH_LONG).show();
                    break;
                case "incorrect_login_password":
                    Toast.makeText(getContext(), "User name and password do not match", Toast.LENGTH_LONG).show();
                    break;
                case "imei_does_not_match":
                    Toast.makeText(getContext(), "Identity number do not match. If you changed the phone please contact the app provider", Toast.LENGTH_LONG).show();
                    break;
                case "login_disabled":
                    Toast.makeText(getContext(), "Your account is disabled. Please contact your app provider", Toast.LENGTH_LONG).show();
                    break;
            }
    }

}
