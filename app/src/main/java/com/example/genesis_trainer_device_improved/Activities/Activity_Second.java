package com.example.genesis_trainer_device_improved.Activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.example.genesis_trainer_device_improved.Fragments.DialogListener;
import com.example.genesis_trainer_device_improved.Fragments.Fragment_Sign_In;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.databinding.ActivitySecondScreenBinding;
import com.example.genesis_trainer_device_improved.helpers.LocaleHelper;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;



public class Activity_Second extends BaseActivityWithSwipeDismiss implements DialogListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "ActivitySecondScreen";
    private static final int CONTENT_VIEW_ID = 111;
    private static final String KEY_RESTART_INTENT = "KEY_RESTART_INTENT";
    private static final int ASK_FOR_IMEI = 71;

    private Fragment_Sign_In fragment;
    private ActivitySecondScreenBinding layoutBinding;

    String deviceID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivitySecondScreenBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());
        initWidgets();
        askPermissionForImei();

    }


    private void askPermissionForImei() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, ASK_FOR_IMEI);
        } else {
//            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//            if (telephonyManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            } else {
                deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                Log.d(TAG, "askPermissionForImei: 1 device id = " + deviceID);
            }

//            }
        }
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (telephonyManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                deviceID = telephonyManager.getImei();
//            }
//            Log.d(TAG, "askPermissionForImei: 2 device id = " + deviceID);
//        }
    }


    @Override
    public void onStart() {
        adjustToScreenSize(layoutBinding.screen2LLayout,0.5f,0.5f);
        super.onStart();

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton_russian:
                LocaleHelper.setLocale(this, "ru");
                //      LocaleHelper.setLocale(App.getmContext(),"ru");
                break;
            case R.id.radioButton_german:
                break;
            case R.id.radioButton_english:
                LocaleHelper.setLocale(this, "en");
                //     LocaleHelper.setLocale(App.getmContext(),"en");

                break;
        }
        recreateActivity();
    }

    private void recreateActivity() {
        Intent mStartActivity = new Intent(this, Starting_Logo_Activity.class);
        int mPendingIntentId = 123456;
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        if (mgr != null) {
            mgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000, mPendingIntent);
        }

        System.exit(0);

    }
    //public methods
    /*
     * on Button button_createNewUser click launches activity that creates master profile. TODO send data to server and parse list of acceptable devices.
     */;

    public void onClick_CreateNewProfile(View view) {
        Log.d(TAG, "onClick_CreateNewProfile:============== ");

        if (deviceID != null && deviceID.length() > 0) {
            Intent intent = new Intent(Activity_Second.this, Activity_Create_User.class);
            intent.putExtra("imei", deviceID);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Cant create new profile without giving permission for phone id", Toast.LENGTH_SHORT).show();
            askPermissionForImei();
        }

    }


    DebouncedClickListener listener = new DebouncedClickListener(1000) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.img_btn_2s_creatProfile:
                    Log.d(TAG, "onClick: case img_btn_2s_creatProfile");
                    onClick_CreateNewProfile(v);
                    break;
                case R.id.btn_SignIn:
                    onClick_Btn_SignIn(v);
                    break;
            }
        }
    };


    public void onClick_buttonBack(View view) {
        onBackPressed();
    }

    /*
     * Show fragment for signing in
     */
    public void onClick_Btn_SignIn(View view) {
        if (deviceID != null) {
            fragment = new Fragment_Sign_In();
            Bundle bundle = new Bundle();
            bundle.putString("imei", deviceID);
            fragment.setArguments(bundle);
            fragment.registerObserver(getLifecycle());
            fragment.show(getSupportFragmentManager(), Fragment_Sign_In.class.getSimpleName());
        } else {
            Toast.makeText(this, "cant login without giving telephony permission", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Callback interface From fragment, when user presses sign in (again every 1000 mili second can press that button, if data is correct launch
     * Trainer_Activity where you set up your gym trainers profile
     */
    @Override
    public void onDialogSignInClick(DialogFragment dialog) {

        if (dialog.getView() == null) return;
        addDisposable(Observable.just(dialog.getView()).debounce(800, TimeUnit.MILLISECONDS).subscribe(subscriber -> {
            dialog.onDestroyView();
            Intent intent = new Intent(Activity_Second.this, Trainer_Activity.class);
            startActivity(intent);
        }));


    }

    private void initWidgets() {

        layoutBinding.btnSignIn.setOnClickListener(listener);
        layoutBinding.imgBtn2sCreatProfile.setOnClickListener(listener);

        layoutBinding.radiogroupLanguages.setOnCheckedChangeListener(this);

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    private void stopFragment() {
        if (fragment != null) {
            getLifecycle().removeObserver(fragment);
            fragment.onDestroyView();
        }
    }

    @Override
    protected void onStop() {
        stopFragment();
        super.onStop();
    }


    @Override
    protected void onDestroy() {

        stopFragment();
        super.onDestroy();
    }


}
