package com.example.genesis_trainer_device_improved.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.ClientWraper;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.Services.BluetoothService;
import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.helpers.ImageHelper;
import com.example.genesis_trainer_device_improved.helpers.LocaleHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.example.genesis_trainer_device_improved.helpers.Constants.ACTION_START_SERVICE;


public class BaseActivityWithSwipeDismiss extends AppCompatActivity {
    private static final String TAG = "BaseActivityWithSwipeDi";
        private static final int SWIPE_MIN_DISTANCE = 500;
        private static final int SWIPE_MAX_OFF_PATH = 600;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;
        private GestureDetectorCompat gestureDetector;
        private CompositeDisposable compositeDisposable;
        public ImageHelper imageHelper;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate:========================= base ");
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            imageHelper = new ImageHelper();
            settoolbar();
            setLanguage();
            Intent intent = new Intent(this, BluetoothService.class);
            intent.setAction(ACTION_START_SERVICE);
            startService(intent);
        }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart:===================== base");

        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause:================= base");
        super.onPause();
    }

    public void addDisposable(Disposable d){
        Log.d(TAG, "addDisposable: adding compositeDisposable");
            if(compositeDisposable ==null) compositeDisposable = new CompositeDisposable();
             compositeDisposable.add(d);
    }
    public void disposeDisposables(){
        Log.d(TAG, "disposeDisposables: dispose compositeDisposable");
     if (compositeDisposable!=null)  compositeDisposable.clear();

    }

    public boolean requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Constants.CAMERA_REQUEST);
            return false;
        }
    }

    public boolean requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_STORAGE_REQUEST);
            return false;
        }
    }


    private void setLanguage(){
//        String str=  LocaleHelper.getLanguage(App.getmContext());
        String str=  LocaleHelper.getLanguage(this);
        String str2=  LocaleHelper.getLanguage(this);
        if (str2 !=null) {

//            LocaleHelper.setLocale(App.getmContext(), str);
            LocaleHelper.setLocale(this, str);
            LocaleHelper.setLocale(this, str2);
            Log.d(TAG, " STR IS " + str);
            Log.d(TAG, " STR IS " + str2);
        }
    }

        public void settoolbar(){
            ActionBar bar = getSupportActionBar();
            if (bar != null) {
                bar.setTitle("Genesis Fitness Pro ");
                bar.setDisplayShowHomeEnabled(true);
                bar.setLogo(R.drawable.logo_appbar);
                bar.setDisplayUseLogoEnabled(true);
            }
            //  gestureDetector = new GestureDetectorCompat(this,new SwipeDetector());
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        }

    public String getCurrentDateAndTime(){
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }

    public List<ClientWraper> addClientsToClientWrapper(List<Client> list){
            List<ClientWraper> l2 = new ArrayList<>();
            for (Client c : list){
                l2.add(new ClientWraper(c,false));
            }
           return l2;
    }

    public void adjustToScreenSize(View v, float x, float y){
        boolean large = (getResources().getConfiguration().screenLayout  &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE;
        boolean normal = (getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL;
        boolean small = (getResources().getConfiguration().screenLayout  &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL;
        Log.d(TAG, "adjustToScreenSize: adjusting screen = " + large +" "+ normal +" "+ small);
        if (normal|| small){
            Log.d(TAG, "adjustToScreenSize: adjusting screen = " + large +" "+ normal +" "+ small);
            v.setScaleX(x);
            v.setScaleY(y);
        }

    }

    public String parseEditText(EditText editText) {
        String text;
        if (editTextNotEmpty(editText)) {
            text = editText.getText().toString().trim();
            return text;
        }
        else{

            return null;
        }
    }


    public int parseStringForInt(EditText editText) {
        String text;
        if (editTextNotEmpty(editText)) {
            text = editText.getText().toString().trim();
            Log.d(TAG, "parseStringForInt: parsed int = " + text);
            return Integer.parseInt(text);

        }

        return 0;

    }

    public void setColorOfEmptyField(EditText edit) {
        edit.setHintTextColor(Color.RED);
    }

    public boolean passwordMatch(EditText password1, EditText password2) {
        String pass1 = password1.getText().toString();
        String pass2 = password2.getText().toString();

        if (pass1.length() >= 6) {
            if (pass1.contentEquals(pass2)) {
                return true;
            }else{
               createToast("Passwords do not match");
            }

        } else {
           createToast( "password needs contain minimum of 6 characters, yours contain ");
        }
        return false;
    }


    public boolean editTextNotEmpty(EditText editText) {
        if (editText.getText().toString().trim().length()>0) {

            return true;
        } else {
            //setColorOfEmptyField(editText);
            return false;
        }
    }

    public boolean stringNotEmpty(String str){
            if (str.length()>0) return true;
            else{
                return false;
            }
    }


    public  boolean fieldsEmptyCheck(EditText... editTexts){

        for (EditText text : editTexts){
            return  editTextNotEmpty(text);
        }
        return false;
    }


    public  boolean isEmailValid(CharSequence email) {

        if ( email !=null && email.length()>0 && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return true;
        }else{
            createToast(getString(R.string.email_not_valid));
            return false;
        }
    }

    public  boolean isValidDateFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            Log.d(TAG, "isValidDateFormat: vlue = "+date);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "input Date is incorrect dd/MM/yyyy", Toast.LENGTH_SHORT).show();

        }
        return date != null;
    }

    public void createToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }




    @Override
    protected void onStop() {
        Log.d(TAG, "onStop:===================== base ");
        disposeDisposables();
        super.onStop();
    }



    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ==================base");

        super.onDestroy();
    }
}
