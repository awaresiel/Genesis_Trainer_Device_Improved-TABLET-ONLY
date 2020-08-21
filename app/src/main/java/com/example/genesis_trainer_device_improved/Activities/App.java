package com.example.genesis_trainer_device_improved.Activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.example.genesis_trainer_device_improved.Entity.Electrode;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
import com.example.genesis_trainer_device_improved.Services.BluetoothService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.example.genesis_trainer_device_improved.helpers.Constants.ACTION_START_SERVICE;

//import leakcanary.AppWatcher;
//import leakcanary.OnObjectRetainedListener;
//import leakcanary.LeakCanary;

public class App extends Application {
    private static final String TAG = "App";

//    private AppWatcher refWatcher;
private static WeakReference<Context> mContext ;
ArrayList<UserTrainingSettingsTable> checkboxStates = new ArrayList<>();
   private Electrode setUpWorkoutOnElectrode;



    private long timer=0;



    @Override
    public void onCreate() {
        super.onCreate();
        mContext = new WeakReference<Context>(this);

        setUpWorkoutOnElectrode = new Electrode();

        timer = TimeUnit.MINUTES.toMillis(400);
        Log.d(TAG, "onCreate: timer = " + TimeUnit.MILLISECONDS.toMinutes(timer));
//        AppCenter.start(this, "0597814e-ae18-49f6-97f5-c97f8590a168",
//                Analytics.class, Crashes.class);
//        AppWatcher.INSTANCE.getObjectWatcher().addOnObjectRetainedListener(new OnObjectRetainedListener() { TODO remove APPCENTER debug impl
//            @Override
//            public void onObjectRetained() {
//                List  list = AppWatcher.INSTANCE.getObjectWatcher().getRetainedObjects();
////
//                for (Object o : list){
//
//                    Log.d(TAG, "onCreate: leaking object name ===== " + o.getClass().getSimpleName());
//                    Log.d(TAG, "onCreate: leaking object toString ===== " + o.toString());
//
//
//                }
//            }
//        });


//        for (Object o : list){
//            Log.d(TAG, "onCreate: o = " + o.getClass().getSimpleName());
//        }
    }

    public static Context getmContext() {
        return mContext.get();


    }
    public long getTimer() {
        return timer;
    }
    public Boolean minusTime(long timeMilis){
        timer = timer-timeMilis;

        Log.d(TAG, "minusTime: timer = " + TimeUnit.MILLISECONDS.toMinutes(timer));
        Log.d(TAG, "minusTime: timeMilis = " + TimeUnit.MILLISECONDS.toMinutes(timeMilis));
        if (timer >1){
            Log.d(TAG, "minusTime: returning true");
           return true;
        }else{
            Log.d(TAG, "minusTime: returning false");
            return false;
        }

    }
    public void setTime(long minutes){
        timer= TimeUnit.MINUTES.toMillis(minutes);
        Log.d(TAG, "setTime: timer=" + TimeUnit.MILLISECONDS.toMinutes(timer));
    }



}
