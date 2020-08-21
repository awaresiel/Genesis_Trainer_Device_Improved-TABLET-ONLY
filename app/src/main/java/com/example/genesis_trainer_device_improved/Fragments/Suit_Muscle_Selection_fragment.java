package com.example.genesis_trainer_device_improved.Fragments;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.genesis_trainer_device_improved.Activities.Chose_Training_Holder_Activity_For_Fragments;
import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Electrode;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
import com.example.genesis_trainer_device_improved.Fragments.adapters.DeviceDisplayAdapter;
import com.example.genesis_trainer_device_improved.Fragments.adapters.DeviceDisplayContent;
import com.example.genesis_trainer_device_improved.R;
import com.example.genesis_trainer_device_improved.ViewModel.DebouncedClickListener;
import com.example.genesis_trainer_device_improved.databinding.FragmentTrainingSetupBinding;
import com.example.genesis_trainer_device_improved.databinding.FrontBackSuitsTogetherLayoutBinding;
import com.example.genesis_trainer_device_improved.databinding.HumanSuitBackLayoutBinding;
import com.example.genesis_trainer_device_improved.databinding.HumanSuitFrontLayoutBinding;
import com.example.genesis_trainer_device_improved.helpers.Check_Which_Sub_Program;
import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_EASY;
import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_HARD;
import static com.example.genesis_trainer_device_improved.helpers.Constants.INTENSITY_MEDIUM;

public class Suit_Muscle_Selection_fragment extends Fragment implements ISuit_Muscle_Selection_fragment, AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
   private static final String TAG = "Suit_Muscle_Selection_f";
    private Chose_Training_Holder_Activity_For_Fragments activity;
    private Trainer trainer;
    private Client client;
    private Electrode electrode;
    private UserTrainingSettingsTable trainingSettings;
    private String selectedDevice;
    private String selectedDeviceAddress;
    private int electrodeNumber;

    private FragmentTrainingSetupBinding layoutBinding;
    private HumanSuitBackLayoutBinding suitBackBinding;
    private HumanSuitFrontLayoutBinding suitFrontBinding;
    private FrontBackSuitsTogetherLayoutBinding frontBackSuitsBinding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: view created => attached");
        layoutBinding = FragmentTrainingSetupBinding.inflate(getLayoutInflater());
        frontBackSuitsBinding = layoutBinding.include2;
        suitBackBinding = frontBackSuitsBinding.includeSuitBack;
        suitFrontBinding = frontBackSuitsBinding.includeSuitFront;
        electrode = new Electrode();
        if (getActivity() instanceof Chose_Training_Holder_Activity_For_Fragments) {
            activity = (Chose_Training_Holder_Activity_For_Fragments) getActivity();
            activity.setTrainingSetupListener(this);
            activity.setMenuButtonsVisibility(false);
            trainingSettings = new UserTrainingSettingsTable();
            setListeners();
            setClient();
            setTrainer();
            instantiateProgramAdapter(activity);
            instantiateSubProgramAdapter(activity, R.array.subprogram_basic);
            instantiateDevicesAdapter(activity);
            if (savedInstanceState != null) {
                loadUserTrainingOnConfingChanged(savedInstanceState.getIntArray(Constants.EDIT_CLIENT_ID));
            }
        }

        return layoutBinding.getRoot();
    }

    public void loadUserTrainingOnConfingChanged(int[] selection) {
        if (selection == null) return;
        activity.addDisposable(activity.getTrainingSettingsModel().loadTrainingSettingsByID(selection[0]).subscribe(table -> {
            new Handler().postDelayed(() -> loadOnConfigurationChanged(table, selection), 200);
        }));

    }


    private void instantiateProgramAdapter(Context c) {
        String[] programs = getResources().getStringArray(R.array.programs_list);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(c, R.layout.spinner_item, R.id.spinnerItemTextview, programs);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        layoutBinding.spinnerProgram.setAdapter(arrayAdapter);
    }

    private void instantiateSubProgramAdapter(Context c, int resourceID) {
        String[] subPrograms = getResources().getStringArray(resourceID);
        ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(c, R.layout.spinner_item, R.id.spinnerItemTextview, subPrograms);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        layoutBinding.spinnerSubProgram.setAdapter(arrayAdapter);
    }

    private void instantiateDevicesAdapter(Context c) {
        activity.getDevicesModel().getBtDevicesList().observe(getViewLifecycleOwner(), devicesList -> {
            DeviceDisplayAdapter arrayAdapter = new DeviceDisplayAdapter(c, devicesList);
            layoutBinding.spinnerConnectedDevicesDisplay.setAdapter(arrayAdapter);
            checkSelectedBTDevices(arrayAdapter);
        });
    }

    private void checkSelectedBTDevices(DeviceDisplayAdapter adapter) {
        if (client == null) return;
        for (int i = 0; i < adapter.getCount(); i++) {
            DeviceDisplayContent value = (DeviceDisplayContent) adapter.getItem(i);
            if (value == null || client.getDeviceAddress() == null) return;

            if (client.getDeviceAddress().equals(value.getDeviceName())) {
                value.setImageVisibility(true);
            }
            layoutBinding.spinnerConnectedDevicesDisplay.setAdapter(adapter);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_program:
                switch (position) {
                    case 0:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_basic);
                        break;
                    case 1:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_cardio);
                        break;
                    case 2:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_strength);
                        break;
                    case 3:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_fat_burning);
                        break;
                    case 4:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_relaxation);
                        break;
                    case 5:
                        instantiateSubProgramAdapter(activity, R.array.subprogram_massage);
                        break;
                }
                break;

            case R.id.spinner_connectedDevicesDisplay:
                DeviceDisplayContent selected = (DeviceDisplayContent) parent.getSelectedItem();
                selectedDevice = selected.getDeviceName();
                selectedDeviceAddress = selected.getDevice().getAddress();
                break;
            case R.id.spinner_subProgram:
                trainingSettings.setTrainingName(parent.getSelectedItem().toString());
                setUpParamters(parent.getSelectedItem().toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        instantiateProgramAdapter(activity);
        instantiateSubProgramAdapter(activity, R.array.subprogram_basic);

    }


    public void setClient() {
        activity.getClientModel().getClient().observe(getViewLifecycleOwner(), client -> {
            this.client = client;
            if (client.getClientProfileImage() != null) {
                layoutBinding.imageButtonSelectedClient.setImageBitmap(BitmapFactory.decodeFile(client.getClientProfileImage()));
            } else {
                if (activity != null){
                  Drawable d = activity.getDrawable(R.drawable.ad_user_icon_80);
                    layoutBinding.imageButtonSelectedClient.setBackground(d);
                }
            }
            layoutBinding.textViewClientNameTrainingSelection.setText(client.getClientName());
        });
    }


    public void setTrainer() {
        activity.getTrainerModel().getTrainer().observe(getViewLifecycleOwner(), trainer -> {
            this.trainer = trainer;

            if (trainer.getTrainerProfileImage() != null) {
                layoutBinding.imageButtonSelectedTrainer.setImageBitmap(BitmapFactory.decodeFile(trainer.getTrainerProfileImage()));
            } else {

                if (activity != null){
                    Drawable d = activity.getDrawable(R.drawable.ad_user_icon_80);
                    layoutBinding.imageButtonSelectedTrainer.setBackground(d);
                }

            }
            layoutBinding.textViewTrainerNameTrainingSelection.setText(trainer.getTrainerName());
        });
    }

    @Override
    public void notifyListChanged() {
        //todo not using atm, delete
        instantiateDevicesAdapter(activity);
    }



    private void incValue(EditText et) {
        int valueInc = activity.parseStringForInt(et);
        if (valueInc >= 99) valueInc = 98;
        et.setText(String.valueOf(++valueInc));
    }

    private void decValue(EditText et) {
        int valueInc = activity.parseStringForInt(et);
        if (valueInc <= 0) valueInc = 1;
        et.setText(String.valueOf(--valueInc));
    }

    private void setUpParamters(String subprogram) {
        //once subprogram was chosen we will set up variables, we are taking name of the item from clicked position and passing it to our static method which will
        //give subprogram parameters that equals the name of selected subprogram
        Check_Which_Sub_Program.Check_SubProgram(subprogram);

        layoutBinding.etSetUpDuration.setText(String.valueOf(Check_Which_Sub_Program.getTimeOfTraining()));

        layoutBinding.etSetUpTimeStimulation.setText(String.valueOf(Check_Which_Sub_Program.getTimeofStimulation_ms() / 1000));

        layoutBinding.etSetUpTimeRest.setText(String.valueOf(Check_Which_Sub_Program.getRestTme_ms() / 1000));

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.radioButton_easy:
                if (isChecked) {
                    Check_Which_Sub_Program.setIntensityhOfTraining(INTENSITY_EASY);
                }
                break;
            case R.id.radioButton_medium:
                if (isChecked) {
                    Check_Which_Sub_Program.setIntensityhOfTraining(INTENSITY_MEDIUM);
                }
                break;
            case R.id.radioButton_hard:
                if (isChecked) {
                    Check_Which_Sub_Program.setIntensityhOfTraining(INTENSITY_HARD);
                }
                break;
        }
    }

    private void matchElectrodeNumbersWithClickedImages() {
        electrodeNumber = 0;
        if (trainingSettings.isChest()) electrodeNumber += Constants.ELECTRODE_7;
        if (trainingSettings.isExtra()) electrodeNumber += Constants.ELECTRODE_8;
        if (trainingSettings.isHands()) electrodeNumber += Constants.ELECTRODE_9;
        if (trainingSettings.isAbdomen()) electrodeNumber += Constants.ELECTRODE_4;
        if (trainingSettings.isQuadriceps()) electrodeNumber += Constants.ELECTRODE_10;
        if (trainingSettings.isTrapez()) electrodeNumber += Constants.ELECTRODE_5;
        if (trainingSettings.isBack()) electrodeNumber += Constants.ELECTRODE_2;
        if (trainingSettings.isLowerBack()) electrodeNumber += Constants.ELECTRODE_1;
        if (trainingSettings.isGlutes()) electrodeNumber += Constants.ELECTRODE_3;
        if (trainingSettings.isHamstrings()) electrodeNumber += Constants.ELECTRODE_6;
        if (trainingSettings.isShoulder()) electrodeNumber += Constants.ELECTRODE_11;
        if (trainingSettings.isTriceps()) electrodeNumber += Constants.ELECTRODE_12;
    }

    //TODO dont forget to add image of selected device once device is selected, spinner item gets reset on each click so check selected devices before

    private void saveTraining() {
        if (activity.fieldsEmptyCheck(layoutBinding.etSetUpDuration, layoutBinding.etSetUpTimeStimulation, layoutBinding.etSetUpTimeRest)) {
            matchElectrodeNumbersWithClickedImages();
            client.setDeviceAddress(selectedDevice);
            trainingSettings.duration = activity.parseStringForInt(layoutBinding.etSetUpDuration);
            trainingSettings.timeOfStimulation_ms = activity.parseStringForInt(layoutBinding.etSetUpTimeStimulation);
            trainingSettings.restTime_ms = activity.parseStringForInt(layoutBinding.etSetUpTimeRest);
            trainingSettings.whichElectrodes = electrodeNumber;
            trainingSettings.setBtAddress(selectedDeviceAddress);

            byte[] array = electrode.setTraining(Constants.FIRST_TEN_ELECTRODES,
                    Check_Which_Sub_Program.getTypeOfImpulse_DuringStimulationCb(),
                    0, 0, 1,
                    Check_Which_Sub_Program.getCf() / Check_Which_Sub_Program.getPy(),
                    trainingSettings.timeOfStimulation_ms * 10000 / Check_Which_Sub_Program.getPy(),
                    trainingSettings.restTime_ms * 10000 / Check_Which_Sub_Program.getPy(),
                    Check_Which_Sub_Program.getIncrease_Ci() / Check_Which_Sub_Program.getPy(),
                    Check_Which_Sub_Program.getDecrease_Cj() / Check_Which_Sub_Program.getPy());

            trainingSettings.setIntensity(Check_Which_Sub_Program.getIntensityhOfTraining());

            byte[] frequency = electrode.setFrequency(Check_Which_Sub_Program.getPx(), Check_Which_Sub_Program.getPy());

            layoutBinding.spinnerConnectedDevicesDisplay.getSelectedView()
                    .findViewById(R.id.imageView_deviceConnectedIndicator2)
                    .setVisibility(View.VISIBLE);

            trainingSettings.setTrainingSetup(array);
            trainingSettings.setTrainingFrequency(frequency);
            trainingSettings.setClientId(client.getClientId());

            activity.addDisposable(activity.getTrainingSettingsModel().addUserTrainingSettingsTable(trainingSettings)
                    .subscribe(s -> Log.d(TAG, "saveCurrentTraining: "), e -> activity.showStackTraceError(e, "saveTraining")));
            activity.notifyRecyclerReset();

            Log.d(TAG, "saveTraining: SAVING = " + trainingSettings.toString());
        } else {
            activity.createToast("All fields must be filled");
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        int[] selection = saveOnConfigurationChanged();
        outState.putIntArray(Constants.EDIT_CLIENT_ID, selection);
    }


    public int[] saveOnConfigurationChanged() {
        Log.d(TAG, "saveOnConfigurationChanged: ");
        selectedDevice = null;
        selectedDeviceAddress = null;
        saveTraining();
        int id = client.getClientId();
        int pos1 = layoutBinding.spinnerConnectedDevicesDisplay.getSelectedItemPosition();
        int pos2 = layoutBinding.spinnerProgram.getSelectedItemPosition();
        int pos3 = layoutBinding.spinnerSubProgram.getSelectedItemPosition();
        int[] selection = new int[5];
        selection[0] = id;
        selection[1] = pos1;
        selection[2] = pos2;
        selection[3] = pos3;
        selection[4] = trainer.getTrainerId();

        return selection;
    }


    public void loadOnConfigurationChanged(UserTrainingSettingsTable table, int[] selection) {
      //  Log.d(TAG, "loadOnConfigurationChanged: " + table.toString());
        Log.d(TAG, "loadOnConfigurationChanged: id " + selection[0]);
        Log.d(TAG, "loadOnConfigurationChanged: spinnerConnectedDevicesDisplay "+ selection[1]);
        Log.d(TAG, "loadOnConfigurationChanged: spinnerProgram "+ selection[2]);
        Log.d(TAG, "loadOnConfigurationChanged: spinnerSubProgram "+ selection[3]);
        layoutBinding.spinnerConnectedDevicesDisplay.setSelection(selection[1]);
        layoutBinding.spinnerProgram.setSelection(selection[2]);
        layoutBinding.spinnerSubProgram.setSelection(selection[3]);

        switchAlpha(suitFrontBinding.imageButtonLeftArm, table.isHands());
        switchAlpha(suitFrontBinding.imageButtonRightArm, table.isHands());
        switchAlpha(suitFrontBinding.imageButtonQuads, table.isQuadriceps());
        switchAlpha(suitFrontBinding.imageButtonAbs, table.isAbdomen());
        switchAlpha(suitFrontBinding.imageButtonChest, table.isChest());

        switchAlpha(suitBackBinding.imageButtonBackLeftArm, table.isTriceps());
        switchAlpha(suitBackBinding.imageButtonBackRightArm, table.isTriceps());
        switchAlpha(suitBackBinding.imageButtonBack, table.isBack());
        switchAlpha(suitBackBinding.imageButtonLowerBack, table.isLowerBack());
        switchAlpha(suitBackBinding.imageButtonButt, table.isGlutes());
        switchAlpha(suitBackBinding.imageButtonHamstrings, table.isHamstrings());
        switchAlpha(suitBackBinding.imageButtonTrapesius, table.isTrapez());

        electrodeNumber = table.whichElectrodes;

        switch (table.getIntensity()) {
            case INTENSITY_EASY:
                layoutBinding.radioButtonEasy.performClick();
                break;
            case INTENSITY_MEDIUM:
                layoutBinding.radioButtonMedium.performClick();
                break;
            case INTENSITY_HARD:
                layoutBinding.radioButtonHard.performClick();
                break;
        }
        new Handler().postDelayed(() -> {
            layoutBinding.etSetUpDuration.setText(String.valueOf(table.duration));
            layoutBinding.etSetUpTimeStimulation.setText(String.valueOf(table.timeOfStimulation_ms));
            layoutBinding.etSetUpTimeRest.setText(String.valueOf(table.restTime_ms));
        }, 500);

        trainingSettings = table;
    }


    private void switchAlpha(View v, boolean val) {
        if (val) {
            v.setAlpha(1);
        } else {
            v.setAlpha(0);
        }
      //  Log.d(TAG, "switchAlpha: v.getalpha= " +v.getAlpha() + " val= "+ val + " id= "+ v.getId());

    }


    private void setAlphaToZero(View... v) {
        for (View view : v)
            view.setAlpha(0f);
    }


    private void setListeners() {

        suitFrontBinding.imageButtonChest.setOnClickListener(listener);
        suitFrontBinding.imageButtonLeftArm.setOnClickListener(listener);
        suitFrontBinding.imageButtonRightArm.setOnClickListener(listener);
        suitFrontBinding.imageButtonQuads.setOnClickListener(listener);
        suitFrontBinding.imageButtonAbs.setOnClickListener(listener);

        suitBackBinding.imageButtonBack.setOnClickListener(listener);
        suitBackBinding.imageButtonTrapesius.setOnClickListener(listener);
        suitBackBinding.imageButtonLowerBack.setOnClickListener(listener);
        suitBackBinding.imageButtonBackRightArm.setOnClickListener(listener);
        suitBackBinding.imageButtonBackLeftArm.setOnClickListener(listener);
        suitBackBinding.imageButtonButt.setOnClickListener(listener);
        suitBackBinding.imageButtonHamstrings.setOnClickListener(listener);

        layoutBinding.buttonSaveSetup.setOnClickListener(listener);

        layoutBinding.buttonIncRestTime.setOnClickListener(listener);
        layoutBinding.buttonDecRestTime.setOnClickListener(listener);

        layoutBinding.buttonDecDuration.setOnClickListener(listener);
        layoutBinding.buttonIncDuration.setOnClickListener(listener);

        layoutBinding.buttonDecTimeOfStimuli.setOnClickListener(listener);
        layoutBinding.buttonIncTimeOfStimuli.setOnClickListener(listener);

        layoutBinding.radioButtonEasy.setOnCheckedChangeListener(this);
        layoutBinding.radioButtonMedium.setOnCheckedChangeListener(this);
        layoutBinding.radioButtonHard.setOnCheckedChangeListener(this);
        layoutBinding.radioButtonEasy.performClick();

        layoutBinding.spinnerProgram.setOnItemSelectedListener(this);
        layoutBinding.spinnerSubProgram.setOnItemSelectedListener(this);
        layoutBinding.spinnerConnectedDevicesDisplay.setOnItemSelectedListener(this);

        setAlphaToZero(
                suitBackBinding.imageButtonBack,
                suitBackBinding.imageButtonHamstrings,
                suitBackBinding.imageButtonButt,
                suitBackBinding.imageButtonBackRightArm,
                suitBackBinding.imageButtonBackLeftArm,
                suitBackBinding.imageButtonLowerBack,
                suitBackBinding.imageButtonTrapesius,

                suitFrontBinding.imageButtonChest,
                suitFrontBinding.imageButtonAbs,
                suitFrontBinding.imageButtonQuads,
                suitFrontBinding.imageButtonRightArm,
                suitFrontBinding.imageButtonLeftArm);

    }

    @Override
    public void onDestroyView() {
        activity = null;
        super.onDestroyView();
    }




    //TODO set trainingSettings states for each body part, if true set false, opposite otherwise and match electrode numbers
    DebouncedClickListener listener = new DebouncedClickListener(300) {
        @Override
        public void onDebouncedClick(View v) {
            switch (v.getId()) {
                case R.id.imageButton_chest:
                    if (!trainingSettings.isChest()) {
                        trainingSettings.setChest(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setChest(false);
                        switchAlpha(v, false);
                    }

                    break;
                case R.id.imageButton_leftArm:
                    if (!trainingSettings.isHands()) {
                        trainingSettings.setHands(true);
                        switchAlpha(suitFrontBinding.imageButtonRightArm, true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setHands(false);
                        switchAlpha(v, false);
                        switchAlpha(suitFrontBinding.imageButtonRightArm, false);
                    }

                    break;
                case R.id.imageButton_rightArm:
                    if (!trainingSettings.isHands()) {
                        trainingSettings.setHands(true);
                        switchAlpha(v, true);
                        switchAlpha(suitFrontBinding.imageButtonLeftArm, true);
                    } else {
                        trainingSettings.setHands(false);
                        switchAlpha(v, false);
                        switchAlpha(suitFrontBinding.imageButtonLeftArm, false);
                    }

                    break;
                case R.id.imageButton_abs:
                    if (!trainingSettings.isAbdomen()) {
                        trainingSettings.setAbdomen(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setAbdomen(false);
                        switchAlpha(v, false);
                    }
                    break;
                case R.id.imageButton_quads:
                    if (!trainingSettings.isQuadriceps()) {
                        trainingSettings.setQuadriceps(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setQuadriceps(false);
                        switchAlpha(v, false);
                    }

                    break;
                case R.id.imageButton_hamstrings:
                    if (!trainingSettings.isHamstrings()) {
                        trainingSettings.setHamstrings(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setHamstrings(false);
                        switchAlpha(v, false);
                    }
                    break;
                case R.id.imageButton_trapesius:
                    if (!trainingSettings.isTrapez()) {
                        trainingSettings.setTrapez(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setTrapez(false);
                        switchAlpha(v, false);
                    }

                    break;
                case R.id.imageButton_lowerBack:
                    if (!trainingSettings.isLowerBack()) {
                        trainingSettings.setLowerBack(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setLowerBack(false);
                        switchAlpha(v, false);
                    }

                    break;
                case R.id.imageButton_back:
                    if (!trainingSettings.isBack()) {
                        trainingSettings.setBack(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setBack(false);
                        switchAlpha(v, false);
                    }

                    break;
                case R.id.imageButton_backRightArm:
                    if (!trainingSettings.isTriceps()) {
                        trainingSettings.setTriceps(true);
                        switchAlpha(v, true);
                        switchAlpha(suitBackBinding.imageButtonBackLeftArm, true);
                    } else {
                        trainingSettings.setTriceps(false);
                        switchAlpha(v, false);
                        switchAlpha(suitBackBinding.imageButtonBackLeftArm, false);
                    }

                    break;
                case R.id.imageButton_back_leftArm:
                    if (!trainingSettings.isTriceps()) {
                        trainingSettings.setTriceps(true);
                        switchAlpha(v, true);
                        switchAlpha(suitBackBinding.imageButtonBackRightArm, true);
                    } else {
                        trainingSettings.setTriceps(false);
                        switchAlpha(v, false);
                        switchAlpha(suitBackBinding.imageButtonBackRightArm, false);
                    }

                    break;
                case R.id.imageButton_butt:
                    if (!trainingSettings.isGlutes()) {
                        trainingSettings.setGlutes(true);
                        switchAlpha(v, true);
                    } else {
                        trainingSettings.setGlutes(false);
                        switchAlpha(v, false);
                    }
                    break;
                case R.id.button_saveSetup:
                    saveTraining();
                    activity.addClientToList(client);
                    break;
                case R.id.button_incDuration:
                    incValue(layoutBinding.etSetUpDuration);
                    break;
                case R.id.button_decDuration:
                    decValue(layoutBinding.etSetUpDuration);
                    break;
                case R.id.button_incTimeOfStimuli:
                    incValue(layoutBinding.etSetUpTimeStimulation);
                    break;
                case R.id.button_decTimeOfStimuli:
                    decValue(layoutBinding.etSetUpTimeStimulation);
                    break;
                case R.id.button_incRestTime:
                    incValue(layoutBinding.etSetUpTimeRest);
                    break;
                case R.id.button_decRestTime:
                    decValue(layoutBinding.etSetUpTimeRest);
                    break;
            }
        }
    };
}
