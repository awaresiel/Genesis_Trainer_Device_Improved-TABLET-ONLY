package com.example.genesis_trainer_device_improved.Fragments;

import android.app.AlertDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.genesis_trainer_device_improved.Activities.App;
import com.example.genesis_trainer_device_improved.Activities.Chose_Training_Holder_Activity_For_Fragments;
import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Electrode;
import com.example.genesis_trainer_device_improved.Entity.ITimeNotify;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.Services.IDeviceConnectionStates;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.ViewModel.MyProgressDialog;
import com.example.genesis_trainer_device_improved.ViewModel.training_settings_model.TrainingSettingsViewModel;
import com.example.genesis_trainer_device_improved.databinding.TrainingFragmentBinding;
import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.helpers.SharedPreferencesHelper;

import java.util.IllegalFormatCodePointException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_1;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_10;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_11;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_12;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_2;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_3;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_4;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_5;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_6;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_7;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_8;
import static com.example.genesis_trainer_device_improved.helpers.Constants.ELECTRODE_9;

public class TrainingFragment extends Fragment implements ITrainingFragment, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener,
        ITimeNotify, IDeviceConnectionStates {
    private static final String TAG = "TrainingFragment";

    private TrainingFragmentBinding layoutBinding;
    private Chose_Training_Holder_Activity_For_Fragments activity;
    private UserTrainingSettingsTable trainingSettings;
    //    private Trainer trainer;
    private Client client;
    private Electrode electrode;
    private String selectedDevice;
    private int electrodeNumber;
    private int allowedProgress;
    private boolean isPaused;
    private boolean connected;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutBinding = TrainingFragmentBinding.inflate(getLayoutInflater());
        if (getActivity() instanceof Chose_Training_Holder_Activity_For_Fragments) {
            activity = (Chose_Training_Holder_Activity_For_Fragments) getActivity();
            activity.setTrainingListener(this);
            electrode = new Electrode();
            activity.setMenuButtonsVisibility(true);
            setListeners();
            setClient();
            setTextForPersonalSuit();
            isPaused = true;
            connected=false;

        }
        return layoutBinding.getRoot();
    }


    public void setClient() {
        activity.getClientModel().getClient().observe(getViewLifecycleOwner(), client -> {
            this.client = client;
            Log.d(TAG, "setClient: this.client=client id = " + client.getClientId());
            if (client.getClientProfileImage() != null) {
                layoutBinding.imageViewDisplayUser.setImageBitmap(BitmapFactory.decodeFile(client.getClientProfileImage()));
            } else {
                if (activity != null) {
                    Drawable d = activity.getDrawable(R.drawable.ad_user_icon_80);
                    layoutBinding.imageViewDisplayUser.setImageDrawable(d);
                }
            }
            layoutBinding.textviewUserImageName.setText(client.getClientName());
            selectedDevice = client.getDeviceAddress();
            initCheckboxAndSeekbarStates(client.getClientId());
        });
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
//        saveToSharedPreferencesOnFragmentSwitching();
     outState.putBundle("saveTimerStates",saveTimerStates());
        activity.getTrainingSettingsModel().addUserTrainingSettingsTable(trainingSettings).subscribe();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored:isPaused ");

        if (savedInstanceState !=null)
        savedInstanceState = savedInstanceState.getBundle("saveTimerStates");
        loadTimerStates(savedInstanceState);
    }

    private void switchImageOnConnection(boolean connected){
        Log.d(TAG, "switchImageOnConnection: connected = = "+connected);
        if (connected){
            layoutBinding.buttonCurrentlyConnectedDevice.setBackground(activity.getDrawable(R.drawable.icons8_bluetooth_connected_2_48));
        }else{
            layoutBinding.buttonCurrentlyConnectedDevice.setBackground(activity.getDrawable(R.drawable.button_selector));
            layoutBinding.buttonCurrentlyConnectedDevice.setImageDrawable(activity.getDrawable(R.drawable.icons8_bluetooth_2_48));
        }
    }

    public void loadTimerStates( Bundle savedInstanceState){
        Log.d(TAG, "loadTimerStates: entering connected");
        if (savedInstanceState != null){
            Log.d(TAG, "loadTimerStates: !+null connected");
            isPaused = savedInstanceState.getBoolean("isPaused");
            connected = savedInstanceState.getBoolean("isConnected");
            Log.d(TAG, "loadTimerStates: connected ===== "+connected);
            layoutBinding.startTrainingBtnStart.setText(savedInstanceState.getString("start"));
            layoutBinding.startTrainingBtnStop.setText(savedInstanceState.getString("stop"));
            layoutBinding.startTrainingEditTextTimeLimit.setText(savedInstanceState.getString("mins"));
            layoutBinding.startTrainingEditTextTimeLimitSeconds.setText(savedInstanceState.getString("secs"));
            layoutBinding.startTrainingEditTextTimeLimit.setEnabled( savedInstanceState.getBoolean("startTrainingEditTextTimeLimit"));
            layoutBinding.startTrainingEditTextTimeLimitSeconds.setEnabled( savedInstanceState.getBoolean("startTrainingEditTextTimeLimitSeconds"));
            layoutBinding.startTrainingBtnStop.setEnabled( savedInstanceState.getBoolean("startTrainingBtnStop"));

            switchImageOnConnection(connected);

        }
    }

    @Override
    public Bundle saveTimerStates(){
        Log.d(TAG, "saveTimerStates: connected sI = "+connected);
        Bundle outState = new Bundle();
        String start = layoutBinding.startTrainingBtnStart.getText().toString();
        String stop = layoutBinding.startTrainingBtnStop.getText().toString();
        String mins = layoutBinding.startTrainingEditTextTimeLimit.getText().toString();
        String secs = layoutBinding.startTrainingEditTextTimeLimitSeconds.getText().toString();

        trainingSettings.duration = Integer.parseInt(mins);
        Log.d(TAG, "onSaveInstanceState: remaining duration= "+trainingSettings.duration);
        outState.putBoolean("isPaused", isPaused);
        outState.putBoolean("isConnected", connected);
        outState.putString("start",start);
        outState.putString("stop",stop);
        outState.putString("mins",mins);
        outState.putString("secs",secs);
        outState.putBoolean("startTrainingEditTextTimeLimit",  layoutBinding.startTrainingEditTextTimeLimit.isEnabled());
        outState.putBoolean("startTrainingEditTextTimeLimitSeconds", layoutBinding.startTrainingEditTextTimeLimitSeconds.isEnabled());
        outState.putBoolean("startTrainingBtnStop",layoutBinding.startTrainingBtnStop.isEnabled());
        Log.d(TAG, "saveTimerStates: connected === "+connected);
//        Log.d(TAG, "saveTimerStates: budnle size is + "+ getBundleSizeInBytes(outState));
       if (activity!=null&& activity.getService()!=null && activity.getService().getTimer(client.getClientId())!=null)
           activity.getService().getTimer(client.getClientId()).setReinitializeStates(outState);
        return outState;
    }



    //    int getBundleSizeInBytes(Bundle bundle) {
//        Parcel parcel = Parcel.obtain();
//        int size;
//
//        parcel.writeBundle(bundle);
//        size = parcel.dataSize();
//        parcel.recycle();
//
//        return size;
//    }

//    TODO all thats left is to reinitialize connected state when another user try to connect onto same device

    private void checkIfTimerIsRuning(int id,long time,String address){

        if (client ==null ||activity==null || activity.getService()==null)return;
        if (activity.getService().getTimer(id) != null){
            activity.getService().getTimer(id).setInotifyCallback(this);
            activity.getService().getTimer(id).setAddress(address);
        }else{
            activity.getService().createNewCounter(time,id);
            activity.getService().getTimer(id).setInotifyCallback(this);
            activity.getService().getTimer(id).setAddress(address);
        }
    }

    private void connectToIdeviceConnectionListener(){
        if (activity==null || client ==null || activity.getService()==null)return;
        activity.getService().setNotifyDeviceConnectionStatesListener(this);
    }

    @Override
    public UserTrainingSettingsTable getCurrentTraining() {
        return trainingSettings;
    }


    DebouncedClickListener clickListener = new DebouncedClickListener(500) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.startTraining_btn_Start:
                    Log.d(TAG, "onDebouncedClick: isPaused btnStart = "+isPaused);
                    if (isPaused) {
                        startTraining();
                    } else {
                        stopTraining();
                    }
                    break;
                case R.id.startTraining_btn_Stop:
                    Log.d(TAG, "onDebouncedClick: isPaused btnstop= "+isPaused);
                    if (layoutBinding.startTrainingBtnStop.getText().equals(activity.getString(R.string.pause)))
                        pauseTraining();
                    else continueTraining();
                    break;
                case R.id.imageButton_incAllImpulses:
                    increaseAllImpulses();
                    break;
                case R.id.imageButton_decAllImpulses:
                    decreaseAllImpulses();
                    break;
                case R.id.button_currently_connected_device:
                    Log.d(TAG, "onDebouncedClick:  service is ==null==");
                    if (activity.getService()!=null) {
                        Log.d(TAG, "onDebouncedClick: service not null");
                        activity.getService().connect(trainingSettings.getBtAddress());
                    }

                    break;

            }
        }
    };

    private void startTraining() {
        if (layoutBinding.startTrainingBtnStart.getText().toString().equals(activity.getString(R.string.start)) &&
                layoutBinding.startTrainingEditTextTimeLimit.getText().length() > 0 &&
                Integer.parseInt(layoutBinding.startTrainingEditTextTimeLimit.getText().toString().trim()) > 0 &&
                layoutBinding.startTrainingEditTextTimeLimitSeconds.getText().length() > 0
        ) {

            int mins = activity.parseStringForInt(layoutBinding.startTrainingEditTextTimeLimit);
            int sec = activity.parseStringForInt(layoutBinding.startTrainingEditTextTimeLimitSeconds);
            trainingSettings.duration=mins;
            Log.d(TAG, "startTraining: remaining start training = " + trainingSettings.duration);
            mins *=60000;
            sec*=1000;

            long time = mins + sec;
            if (activity.getService().getTimer(client.getClientId()) == null) {
                activity.getService().createNewCounter(time, client.getClientId());
            }
//            activity.getService().getTimer(client.getClientId()).setInotifyCallback(this);
            activity.getService().getTimer(client.getClientId()).setTime(time);
            activity.getService().getTimer(client.getClientId()).startTimer();

            activity.getService().startTraining(trainingSettings.getTrainingFrequency(),
                    trainingSettings.getTrainingSetup(),
                    electrode.startElectrodes(Constants.ALL_ELECTRODES),
                    trainingSettings.getBtAddress());
        }
    }



    private void stopTraining() {
        if (activity.getService().getTimer(client.getClientId()) != null) {
            activity.getService().getTimer(client.getClientId()).cancel();
            activity.messageDevice(electrode.stopElectrode(Constants.ALL_ELECTRODES), trainingSettings.getBtAddress());

        }
    }

    private void pauseTraining() {
        if (activity.getService().getTimer(client.getClientId()) != null)
            activity.getService().getTimer(client.getClientId()).pause();
        activity.messageDevice(electrode.stopElectrode(Constants.ALL_ELECTRODES), trainingSettings.getBtAddress());
    }

    private void continueTraining() {
        if (activity.getService().getTimer(client.getClientId()) != null)
            activity.getService().getTimer(client.getClientId()).continueTimer();


    }

    private void setStartBtnTxtToStart() {
        if (activity!=null)
        layoutBinding.startTrainingBtnStart.setText(activity.getString(R.string.start));
    }

    private void setStartBtnTxtToStop() {
        if (activity!=null)
        layoutBinding.startTrainingBtnStart.setText(activity.getString(R.string.stop));
    }

    private void setPauseBtnTxtToPause() {
        if (activity!=null)
        layoutBinding.startTrainingBtnStop.setText(activity.getString(R.string.pause));
    }

    private void setPauseBtnTxtToResume() {
        if (activity!=null)
        layoutBinding.startTrainingBtnStop.setText(activity.getString(R.string.Resume));
    }

    private void disableTimeInput() {
        layoutBinding.startTrainingEditTextTimeLimit.setEnabled(false);
        layoutBinding.startTrainingEditTextTimeLimitSeconds.setEnabled(false);
    }

    private void enableTimeInput() {
        layoutBinding.startTrainingEditTextTimeLimit.setEnabled(true);
        layoutBinding.startTrainingEditTextTimeLimitSeconds.setEnabled(true);
    }

    @Override
    public void notifyReinitializeWidgets(Bundle states) {
        Log.d(TAG, "notifyReinitializeWidgets: connected");
        loadTimerStates(states);
    }

    @Override
    public void started() {
        disableTimeInput();
        layoutBinding.startTrainingBtnStop.setEnabled(true);
        setStartBtnTxtToStop();
        setPauseBtnTxtToPause();
        isPaused = false;
    }

    @Override
    public void stoped() {
        layoutBinding.startTrainingBtnStop.setEnabled(false);
        isPaused = true;
        enableTimeInput();
        setStartBtnTxtToStart();
        setPauseBtnTxtToPause();
    }

    @Override
    public void paused() {
        layoutBinding.startTrainingBtnStop.setEnabled(true);
        isPaused = true;
        setPauseBtnTxtToResume();
        setStartBtnTxtToStart();
    }

    @Override
    public void resumed() {
        setPauseBtnTxtToPause();
        setStartBtnTxtToStop();
        activity.getService().writeMessage(("<S"+trainingSettings.whichElectrodes+">").getBytes(),trainingSettings.getBtAddress());
        isPaused = false;
    }

    @Override
    public void deviceConnected(String address) {
        Log.d(TAG, "deviceConnected: "+ address);
        if (trainingSettings ==null|| activity==null)return;
        connected = true;
        if (trainingSettings.getBtAddress().equals(address)){
            layoutBinding.buttonCurrentlyConnectedDevice.post(() ->
                    layoutBinding.buttonCurrentlyConnectedDevice.setBackground(activity.getDrawable(R.drawable.icons8_bluetooth_connected_2_48)));
        }
    }

    @Override
    public void deviceDisconnected(String address) {
        Log.d(TAG, "deviceDisconnected: "+address);
        if (trainingSettings ==null|| activity==null)return;
        connected = false;
        if (trainingSettings.getBtAddress().equals(address)) {
            layoutBinding.buttonCurrentlyConnectedDevice.post(() ->{
                    layoutBinding.buttonCurrentlyConnectedDevice.setBackground(activity.getDrawable(R.drawable.button_selector));
                    layoutBinding.buttonCurrentlyConnectedDevice.setImageDrawable(activity.getDrawable(R.drawable.icons8_bluetooth_2_48));
        });
        }
    }

    @Override
    public void updateTimer(int clientsId, long mins, long sec) {
        Log.d(TAG, "updateTimer: client id= " + clientsId + "time= " + mins + " : " + sec);
        if (client.getClientId() == clientsId) {
            Log.d(TAG, "updateTimer: client == clientsId "+ clientsId + " c= "+client.getClientId());
             String displaytime = (sec > 9) ? String.format(Locale.US, "%d" + ":" + "%d", mins, sec) :
                    String.format(Locale.US, "%d" + ":" + "0%d", mins, sec);
           layoutBinding.startTrainingBtnTimeCounter.setText(displaytime);
        }
    }

    @Override
    public void notifyMessage(int clientsId, String message) {
        if (message.equals("Training done")) {
            layoutBinding.startTrainingBtnTimeCounter.setText("00:00");
            setStartBtnTxtToStart();
        }
    }

    private void setProgress() {
        allowedProgress = layoutBinding.seekBarIncreaseAllLevels.getProgress();
        if (trainingSettings != null) trainingSettings.setIncreaseAllLevelsLevel(allowedProgress);
    }

    @Override
    public void loadTraining() {
        Log.d(TAG, "loadTraining: before client ==null");
        if (client == null) return;
        Log.d(TAG, "loadTraining: after client == null");
        stopTraining();
        stoped();
        int loadID = client.getClientId() * 1000;
        activity.addDisposable(activity.getTrainingSettingsModel().loadTrainingSettingsByID(loadID).subscribe(table -> {
            if (table != null) {
                int id = table.getClientId() / 1000;
                table.setClientId(id);
                load(table);
                Log.d(TAG, "loadTraining: table.getClientId() = " + id + " client id = " + client.getClientId());
            }
        }));
    }


    private void load(UserTrainingSettingsTable table) {
        if (table == null || trainingSettings == null) return;
        Log.d(TAG, "load: loadTraining getid table = " + table.getClientId());
        Log.d(TAG, "load: loadTraining time table = " + table.duration);

        String tempAddress = trainingSettings.getBtAddress();
        table.setBtAddress(tempAddress);
        int timeOfStimulation_ms = trainingSettings.timeOfStimulation_ms;
        int restTime_ms = trainingSettings.restTime_ms;
        int duration = trainingSettings.duration;
        String trainingName = trainingSettings.getTrainingName();
        String intensity = trainingSettings.getIntensity();
        String btAddress = trainingSettings.getBtAddress();
        byte[] trainingFrequency = trainingSettings.getTrainingFrequency();
        byte[] trainingSetup = trainingSettings.getTrainingSetup();

        //  trainingSettings.setClientId(table.getClientId());

        trainingSettings = table;
        trainingSettings.timeOfStimulation_ms = timeOfStimulation_ms;
        trainingSettings.restTime_ms = restTime_ms;
        trainingSettings.duration = duration;
        trainingSettings.setTrainingName(trainingName);
        trainingSettings.setIntensity(intensity);
        trainingSettings.setBtAddress(btAddress);
        trainingSettings.setTrainingFrequency(trainingFrequency);
        trainingSettings.setTrainingSetup(trainingSetup);

        layoutBinding.tvCurrentTrainingName.setText(table.getTrainingName());
        layoutBinding.startTrainingEditTextTimeLimit.setText(String.valueOf(table.duration));
        layoutBinding.startTrainingEditTextTimeLimitSeconds.setText("00");
        layoutBinding.tvImpulseStrength.setText(String.valueOf(table.timeOfStimulation_ms));
        layoutBinding.tvImpulsePausa.setText(String.valueOf(table.restTime_ms));

        layoutBinding.seekBarIncreaseAllLevels.setProgress(table.getIncreaseAllLevelsLevel());

        setEnabledCheckboxes(table);
        setEnabledSeekbars(table);
        incrementSeekBarsProgresses(table);

        //TODO write method to restore states by incrementing seekbars by 5 points every 5 seconds

    }

    @Override
    public void setTextForPersonalSuit() {
        layoutBinding.textViewNameChest.setText(R.string.chest);
        layoutBinding.textViewNameHands.setText(R.string.Biceps);
        layoutBinding.textViewNameAbs.setText(R.string.Abdomen);
        layoutBinding.textViewNameQuads.setText(R.string.Quadriceps);
        layoutBinding.textViewNameTrapez.setText(R.string.Trapez);
        layoutBinding.textViewNameBack.setText(R.string.back_spina);
        layoutBinding.textViewNameLowerBack.setText(R.string.lower_back);
        layoutBinding.textViewNameHams.setText(R.string.hamstrings);
        layoutBinding.textViewNameGlutes.setText(R.string.gluteus);
        layoutBinding.textViewNameExtra.setText(R.string.extra);
        layoutBinding.textViewNameShoulder.setText(R.string.shoulder);
        layoutBinding.textViewNameTriceps.setText(R.string.triceps);
    }


    @Override
    public void setTextForGroupTrainingSuits() {
        layoutBinding.textViewNameChest.setText(R.string.chest);
        layoutBinding.textViewNameHands.setText(R.string.Biceps);
        layoutBinding.textViewNameAbs.setText(R.string.Abdomen);
        layoutBinding.textViewNameQuads.setText(R.string.Quadriceps);
        layoutBinding.textViewNameTrapez.setText(R.string.Trapez);
        layoutBinding.textViewNameBack.setText(R.string.back_spina);
        layoutBinding.textViewNameLowerBack.setText(R.string.lower_back);
        layoutBinding.textViewNameHams.setText(R.string.hamstrings);
        layoutBinding.textViewNameGlutes.setText(R.string.gluteus);
        layoutBinding.textViewNameExtra.setText(R.string.Extra_1);
        layoutBinding.textViewNameShoulder.setText(R.string.Extra_2);
        layoutBinding.textViewNameTriceps.setText(R.string.triceps);
    }

    //TODO take care of checkboxes on checkbx clicked
    private void increaseAllImpulses() {
        if (layoutBinding.cbChest.isChecked()) layoutBinding.seekBarChest.incrementProgressBy(1);
        if (layoutBinding.cbHands.isChecked()) layoutBinding.seekBarHands.incrementProgressBy(1);
        if (layoutBinding.cbAbs.isChecked()) layoutBinding.seekBarAbs.incrementProgressBy(1);
        if (layoutBinding.cbQuadriceps.isChecked())
            layoutBinding.seekBarQuadriceps.incrementProgressBy(1);
        if (layoutBinding.cbTrapesius.isChecked())
            layoutBinding.seekBarTrapesius.incrementProgressBy(1);
        if (layoutBinding.cbBack.isChecked()) layoutBinding.seekBarBack.incrementProgressBy(1);
        if (layoutBinding.cbLowerBack.isChecked())
            layoutBinding.seekBarLowerback.incrementProgressBy(1);
        if (layoutBinding.cbGlutes.isChecked()) layoutBinding.seekBarGluteus.incrementProgressBy(1);
        if (layoutBinding.cbHamstrings.isChecked())
            layoutBinding.seekbarHamstrings.incrementProgressBy(1);
        if (layoutBinding.cbExtra.isChecked()) layoutBinding.seekBarExtra.incrementProgressBy(1);
        if (layoutBinding.cbShoulder.isChecked())
            layoutBinding.seekBarShoulder.incrementProgressBy(1);
        if (layoutBinding.cbTriceps.isChecked())
            layoutBinding.seekBarTriceps.incrementProgressBy(1);
        activity.messageDevice(("<U" + electrodeNumber + ">").getBytes(), trainingSettings.getBtAddress());
    }

    private void decreaseAllImpulses() {
        if (!isPaused) {
            if (layoutBinding.cbChest.isChecked())
                layoutBinding.seekBarChest.incrementProgressBy(-1);
            if (layoutBinding.cbHands.isChecked())
                layoutBinding.seekBarHands.incrementProgressBy(-1);
            if (layoutBinding.cbAbs.isChecked()) layoutBinding.seekBarAbs.incrementProgressBy(-1);
            if (layoutBinding.cbQuadriceps.isChecked())
                layoutBinding.seekBarQuadriceps.incrementProgressBy(-1);
            if (layoutBinding.cbTrapesius.isChecked())
                layoutBinding.seekBarTrapesius.incrementProgressBy(-1);
            if (layoutBinding.cbBack.isChecked()) layoutBinding.seekBarBack.incrementProgressBy(-1);
            if (layoutBinding.cbLowerBack.isChecked())
                layoutBinding.seekBarLowerback.incrementProgressBy(-1);
            if (layoutBinding.cbGlutes.isChecked())
                layoutBinding.seekBarGluteus.incrementProgressBy(-1);
            if (layoutBinding.cbHamstrings.isChecked())
                layoutBinding.seekbarHamstrings.incrementProgressBy(-1);
            if (layoutBinding.cbExtra.isChecked())
                layoutBinding.seekBarExtra.incrementProgressBy(-1);
            if (layoutBinding.cbShoulder.isChecked())
                layoutBinding.seekBarShoulder.incrementProgressBy(-1);
            if (layoutBinding.cbTriceps.isChecked())
                layoutBinding.seekBarTriceps.incrementProgressBy(-1);
            activity.messageDevice(("<D" + electrodeNumber + ">").getBytes(), trainingSettings.getBtAddress());
        }
    }

    private void setTextLevel(int progress, int id) {
        switch (id) {
            case R.id.seekBar_chest:
                layoutBinding.strengthtextChest.setText(progress + "%");
                break;
            case R.id.seekBar_hands:
                layoutBinding.strengthtextHands.setText(progress + "%");
                break;
            case R.id.seekBar_abs:
                layoutBinding.strengthtextAbs.setText(progress + "%");
                break;
            case R.id.seekBar_quadriceps:
                layoutBinding.strengthtextQuadriceps.setText(progress + "%");
                break;
            case R.id.seekBar_trapesius:
                layoutBinding.strengthtextTrapesius.setText(progress + "%");
                break;
            case R.id.seekBar_back:
                layoutBinding.strengthtextBack.setText(progress + "%");
                break;
            case R.id.seekBar_lowerback:
                layoutBinding.strengthtextLowerBack.setText(progress + "%");
                break;
            case R.id.seekbar_hamstrings:
                layoutBinding.strengthtextHamstrings.setText(progress + "%");
                break;
            case R.id.seekBar_gluteus:
                layoutBinding.strengthtextGlutes.setText(progress + "%");
                break;
            case R.id.seekBar_extra:
                layoutBinding.strengthtextExtra.setText(progress + "%");
                break;
            case R.id.seekBar_Triceps:
                layoutBinding.strengthtextTriceps.setText(progress + "%");
                break;
            case R.id.seekBar_Shoulder:
                layoutBinding.strengthtextShoulder.setText(progress + "%");
                break;
            case R.id.seekBar_increase_all_levels:

                layoutBinding.strengthTextIncreaseAllLevels.setText(progress + "%");


                break;
        }
    }

    private void setListeners() {
        layoutBinding.cbChest.setOnCheckedChangeListener(this);
        layoutBinding.cbHands.setOnCheckedChangeListener(this);
        layoutBinding.cbAbs.setOnCheckedChangeListener(this);
        layoutBinding.cbQuadriceps.setOnCheckedChangeListener(this);
        layoutBinding.cbTrapesius.setOnCheckedChangeListener(this);

        layoutBinding.cbBack.setOnCheckedChangeListener(this);
        layoutBinding.cbLowerBack.setOnCheckedChangeListener(this);
        layoutBinding.cbGlutes.setOnCheckedChangeListener(this);
        layoutBinding.cbHamstrings.setOnCheckedChangeListener(this);
        layoutBinding.cbExtra.setOnCheckedChangeListener(this);
        layoutBinding.cbShoulder.setOnCheckedChangeListener(this);
        layoutBinding.cbTriceps.setOnCheckedChangeListener(this);

        layoutBinding.seekBarChest.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarHands.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarAbs.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarQuadriceps.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarTrapesius.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarTriceps.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarShoulder.setOnSeekBarChangeListener(this);

        layoutBinding.seekBarBack.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarLowerback.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarGluteus.setOnSeekBarChangeListener(this);
        layoutBinding.seekbarHamstrings.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarExtra.setOnSeekBarChangeListener(this);
        layoutBinding.seekBarIncreaseAllLevels.setOnSeekBarChangeListener(this);

        layoutBinding.imageButtonIncAllImpulses.setOnClickListener(clickListener);
        layoutBinding.imageButtonDecAllImpulses.setOnClickListener(clickListener);
        layoutBinding.startTrainingBtnStart.setOnClickListener(clickListener);
        layoutBinding.startTrainingBtnStop.setOnClickListener(clickListener);
        layoutBinding.buttonCurrentlyConnectedDevice.setOnClickListener(clickListener);

    }

    private void initCheckboxAndSeekbarStates(int id) {
        if (activity == null) return;
        Log.d(TAG, "initCheckboxAndSeekbarStates: id " + id);
        activity.addDisposable(activity.getTrainingSettingsModel().loadTrainingSettingsByID(id).subscribe(table -> {
            if (table == null) return;
            trainingSettings = table;
//            activity.getTrainingSettingsModel().setTable(table);
            layoutBinding.tvCurrentTrainingName.setText(table.getTrainingName());
            layoutBinding.startTrainingEditTextTimeLimit.setText(String.valueOf(table.duration));
            layoutBinding.startTrainingEditTextTimeLimitSeconds.setText("00");
            layoutBinding.tvImpulseStrength.setText(String.valueOf(table.timeOfStimulation_ms));
            layoutBinding.tvImpulsePausa.setText(String.valueOf(table.restTime_ms));
            layoutBinding.tvShowDeviceName.setText(selectedDevice);


          //  table.duration *= 60000; //to turn it into miliseconds

            layoutBinding.seekBarChest.setMax(100);
            layoutBinding.seekBarHands.setMax(100);
            layoutBinding.seekBarAbs.setMax(100);
            layoutBinding.seekBarQuadriceps.setMax(100);
            layoutBinding.seekBarTrapesius.setMax(100);
            layoutBinding.seekBarBack.setMax(100);
            layoutBinding.seekBarLowerback.setMax(100);
            layoutBinding.seekBarGluteus.setMax(100);
            layoutBinding.seekbarHamstrings.setMax(100);
            layoutBinding.seekBarExtra.setMax(100);
            layoutBinding.seekBarTriceps.setMax(100);
            layoutBinding.seekBarShoulder.setMax(100);
            setEnabledCheckboxes(table);
            setEnabledSeekbars(table);
            incrementSeekBarsProgresses(table);
            checkIfTimerIsRuning(id, table.duration,table.getBtAddress());
            connectToIdeviceConnectionListener();
        }));
    }

    private void incrementSeekBarsProgresses(UserTrainingSettingsTable table) {

        layoutBinding.seekBarIncreaseAllLevels.setProgress(table.getIncreaseAllLevelsLevel());
        layoutBinding.seekBarChest.setProgress(table.getChestLevel());
        layoutBinding.seekBarHands.setProgress(table.getHandsLevel());
        layoutBinding.seekBarAbs.setProgress(table.getAbdomenLevel());
        layoutBinding.seekBarQuadriceps.setProgress(table.getQuadricepsLevel());
        layoutBinding.seekBarTrapesius.setProgress(table.getTrapezLevel());
        layoutBinding.seekBarBack.setProgress(table.getBackLevel());
        layoutBinding.seekBarLowerback.setProgress(table.getLowerBackLevel());
        layoutBinding.seekBarGluteus.setProgress(table.getGlutesLevel());
        layoutBinding.seekbarHamstrings.setProgress(table.getHamstringsLevel());
        layoutBinding.seekBarExtra.setProgress(table.getExtraLevel());
        layoutBinding.seekBarTriceps.setProgress(table.getTricepsLevel());
        layoutBinding.seekBarShoulder.setProgress(table.getShoulderLevel());

    }

    private void setEnabledCheckboxes(UserTrainingSettingsTable table) {
        layoutBinding.cbChest.setChecked(table.isChest());
        layoutBinding.cbHands.setChecked(table.isHands());
        layoutBinding.cbAbs.setChecked(table.isAbdomen());
        layoutBinding.cbQuadriceps.setChecked(table.isQuadriceps());
        layoutBinding.cbTrapesius.setChecked(table.isTrapez());
        layoutBinding.cbBack.setChecked(table.isBack());
        layoutBinding.cbLowerBack.setChecked(table.isLowerBack());
        layoutBinding.cbHamstrings.setChecked(table.isHamstrings());
        layoutBinding.cbGlutes.setChecked(table.isGlutes());
        layoutBinding.cbExtra.setChecked(table.isExtra());
        layoutBinding.cbTriceps.setChecked(table.isTriceps());
        layoutBinding.cbShoulder.setChecked(table.isShoulder());
    }

    private void setEnabledSeekbars(UserTrainingSettingsTable table) {
        layoutBinding.seekBarChest.setEnabled(table.isChest());
        layoutBinding.seekBarHands.setEnabled(table.isHands());
        layoutBinding.seekBarAbs.setEnabled(table.isAbdomen());
        layoutBinding.seekBarQuadriceps.setEnabled(table.isQuadriceps());
        layoutBinding.seekBarTrapesius.setEnabled(table.isTrapez());
        layoutBinding.seekBarBack.setEnabled(table.isBack());
        layoutBinding.seekBarLowerback.setEnabled(table.isLowerBack());
        layoutBinding.seekBarGluteus.setEnabled(table.isGlutes());
        layoutBinding.seekbarHamstrings.setEnabled(table.isHamstrings());
        layoutBinding.seekBarExtra.setEnabled(table.isExtra());
        layoutBinding.seekBarTriceps.setEnabled(table.isTriceps());
        layoutBinding.seekBarShoulder.setEnabled(table.isShoulder());

    }

    private void checkboxState(boolean state, SeekBar seekBar, int electrodeNumber) {
        if (!state) {
            this.electrodeNumber -= electrodeNumber;
            seekBar.setEnabled(false);
        } else {
            seekBar.setEnabled(true);
            this.electrodeNumber += electrodeNumber;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (trainingSettings == null) return;
        switch (buttonView.getId()) {
            case R.id.cb_chest:
                checkboxState(isChecked, layoutBinding.seekBarChest, ELECTRODE_1);
                trainingSettings.setChest(isChecked);
                break;
            case R.id.cb_hands:
                checkboxState(isChecked, layoutBinding.seekBarHands, ELECTRODE_9);
                trainingSettings.setHands(isChecked);
                break;
            case R.id.cb_abs:
                checkboxState(isChecked, layoutBinding.seekBarAbs, ELECTRODE_8);
                trainingSettings.setAbdomen(isChecked);
                break;
            case R.id.cb_quadriceps:
                trainingSettings.setQuadriceps(isChecked);
                checkboxState(isChecked, layoutBinding.seekBarQuadriceps, ELECTRODE_10);
                break;
            case R.id.cb_trapesius:
                checkboxState(isChecked, layoutBinding.seekBarTrapesius, ELECTRODE_2);
                trainingSettings.setTrapez(isChecked);
                break;
            case R.id.cb_back:
                checkboxState(isChecked, layoutBinding.seekBarBack, ELECTRODE_5);
                trainingSettings.setBack(isChecked);
                break;
            case R.id.cb_lowerBack:
                checkboxState(isChecked, layoutBinding.seekBarLowerback, ELECTRODE_7);
                trainingSettings.setLowerBack(isChecked);
                break;
            case R.id.cb_hamstrings:
                checkboxState(isChecked, layoutBinding.seekbarHamstrings, ELECTRODE_4);
                trainingSettings.setHamstrings(isChecked);
                break;
            case R.id.cb_glutes:
                checkboxState(isChecked, layoutBinding.seekBarGluteus, ELECTRODE_3);
                trainingSettings.setGlutes(isChecked);
                break;
            case R.id.cb_extra:
                checkboxState(isChecked, layoutBinding.seekBarExtra, ELECTRODE_6);
                trainingSettings.setExtra(isChecked);
                break;
            case R.id.cb_Shoulder:
                checkboxState(isChecked, layoutBinding.seekBarShoulder, ELECTRODE_11);
                trainingSettings.setShoulder(isChecked);
                break;
            case R.id.cb_Triceps:
                checkboxState(isChecked, layoutBinding.seekBarTriceps, ELECTRODE_12);
                trainingSettings.setTriceps(isChecked);
                break;


        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        setTextLevel(progress, seekBar.getId());
        setProgress();
        if (seekBar.getProgress() >= allowedProgress) {
            seekBar.setProgress(allowedProgress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar.getProgress() >= allowedProgress) {
            seekBar.setProgress(allowedProgress);
        }
    }

    public void seekbarStateMessage(int levelEl, int electrodeNumber) {
        activity.messageDevice(electrode.setLevel(electrodeNumber, levelEl), trainingSettings.getBtAddress());
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int level = seekBar.getProgress();
        int id = seekBar.getId();

        switch (id) {
            case R.id.seekBar_chest:
                seekbarStateMessage(level, ELECTRODE_1);
                trainingSettings.setChestLevel(level);
                break;
            case R.id.seekBar_hands:
                seekbarStateMessage(level, ELECTRODE_9);
                trainingSettings.setHandsLevel(level);
                break;
            case R.id.seekBar_abs:
                seekbarStateMessage(level, ELECTRODE_8);
                trainingSettings.setAbdomenLevel(level);
                break;
            case R.id.seekBar_quadriceps:
                seekbarStateMessage(level, ELECTRODE_10);
                trainingSettings.setQuadricepsLevel(level);
                break;
            case R.id.seekBar_trapesius:
                seekbarStateMessage(level, ELECTRODE_2);
                trainingSettings.setTrapezLevel(level);
                break;
            case R.id.seekBar_back:
                seekbarStateMessage(level, ELECTRODE_5);
                trainingSettings.setBackLevel(level);
                break;
            case R.id.seekBar_lowerback:
                seekbarStateMessage(level, ELECTRODE_7);
                trainingSettings.setLowerBackLevel(level);
                break;
            case R.id.seekBar_gluteus:
                seekbarStateMessage(level, ELECTRODE_3);
                trainingSettings.setGlutesLevel(level);
                break;
            case R.id.seekbar_hamstrings:
                seekbarStateMessage(level, ELECTRODE_4);
                trainingSettings.setHamstringsLevel(level);
                break;
            case R.id.seekBar_extra:
                seekbarStateMessage(level, ELECTRODE_6);
                trainingSettings.setExtraLevel(level);
                break;
            case R.id.seekBar_Shoulder:
                seekbarStateMessage(level, ELECTRODE_11);
                trainingSettings.setShoulderLevel(level);
                break;
            case R.id.seekBar_Triceps:
                seekbarStateMessage(level, ELECTRODE_12);
                trainingSettings.setTricepsLevel(level);
                break;
            case R.id.seekBar_increase_all_levels:
                setProgress();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        activity = null;
        super.onDestroyView();
    }
}
