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
        Log.d(TAG, "onCreate: APP CREATE STR IS ");
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


/*
 * In this array list we save UserTrainingSettingsTable table which saves all our checkbox and seekbar states and levels
 */
    public ArrayList<UserTrainingSettingsTable> getCheckboxTable(){
        return checkboxStates;
    }

//nested hashmap test
    /*
        1st integer is clients id
        2nd integer id of checkbox
        3rd hashmap booleanvalues coresponding to clients id
     */

//    private HashMap<Integer,HashMap<Integer, Boolean>> nestedClientsIDCheckboxesSet = new HashMap<Integer, HashMap<Integer, Boolean>>();

    /*
    NOT IN USE, CAN DELETE, ITS REPLACES BY UserTrainingSettingsTable
        integer = clients id
        boolean checkbox state
     */
//  private  HashMap<Integer, Boolean> integerBooleanHashMap = new HashMap<>();

    /*
    integer = clientid
    string = address of clients device
     */

    private HashMap<Integer, String> getUsersAddressMap = new HashMap<>();

    /*
        integer = userId
        int[] = array of user program choices, contains 0-3 fields
                     0 = duration
                     1 = timeofstimulation
                     2 = restTime
                     3 = whichElectrodes wehere chosen
     */

    private HashMap<Integer,int[]> getUsersSetupMap = new HashMap<>();

    /*
        integer = userID
        String = name of subprogram
     */
    private HashMap<Integer, String> userSubProgramChoice = new HashMap<>();

    /*
        byte training
     */
    private HashMap<Integer,byte[]> getUsersTrainingSetupMap = new HashMap<>();
    /*
        byte frequency
     */

    private HashMap<Integer,byte[]> getUsersTrainingFrequencyMap = new HashMap<>();



    public HashMap<Integer, int[]> getGetUsersSetupMap() {
        return getUsersSetupMap;
    }

    int cb=0;
   int Cc=0;
    int Cd=0;
    int Ce=0;
    int Cf=0;
    int Cg=0;
    int Ch=0;
    int Ci=0;
    int Cj=0;
    int Py=0;
    public byte[] setupWorkoutForElectrodes(int electrodes,int Cb,int Cc,int Cd,int Ce,
                                           int Cf,int Cg,int Ch,int Ci,int Cj,int Py){

        this. cb=Cb;
        this. Cc=Cc;
        this. Cd=Cd;
        this. Ce=Ce;
        this. Cf=Cf;
        this. Cg=Cg;
        this. Ch=Ch;
        this. Ci=Ci;
        this. Cj=Cj;
        this. Py=Py;

      byte[] array = setUpWorkoutOnElectrode.setTraining(electrodes,Cb, Cc, Cd, Ce, Cf / Py,
                Cg * 10000 / Py, Ch * 10000 / Py,
                Ci / Py, Cj / Py);
        Log.d(TAG, "setupWorkoutForElectrodes: String= " +new String(array));
      return array;
    }

    public byte[] getTrainingSetup(int electrodes){
        byte[] array = setUpWorkoutOnElectrode.setTraining(electrodes,cb, Cc, Cd, Ce, Cf / Py,
                Cg * 10000 / Py, Ch * 10000 / Py,
                Ci / Py, Cj / Py);
        Log.d(TAG, "setupWorkoutForElectrodes: String= " +new String(array));
        return array;
    }


    public HashMap<Integer, byte[]> getGetUsersTrainingFrequencyMap() {
        return getUsersTrainingFrequencyMap;
    }


//        public void resetCheckBoxID_BooleanValueMap(){
//            integerBooleanHashMap.clear();
//        }


    public HashMap<Integer, String> getGetUsersAddressMap() {
        return getUsersAddressMap;
    }

    public HashMap<Integer, String> getUserSubProgramChoice() {
        return userSubProgramChoice;
    }

    public HashMap<Integer, byte[]> getGetUsersTrainingSetupMap() {
        return getUsersTrainingSetupMap;
    }





}
