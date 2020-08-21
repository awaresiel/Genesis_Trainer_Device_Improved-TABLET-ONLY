package com.example.genesis_trainer_device_improved.helpers;


import com.example.genesis_trainer_device_improved.Activities.App;
import com.example.genesis_trainer_device_improved.R;

public class Constants {
    //name of sqlite room table
    public static final String TABLE_NAME = "User_Storage";
    public static final String TABLE_TRAINER_NAME = "Trainer_Storage";
    public static final String TABLE_CLIENT_NAME = "Client_Storage";
    public static final String TABLE_CLIENT_SETTING = "Client_Setting_Storage";
    public static final String ACTION_START_SERVICE = "com.awaresiel.genesistrainerdevice.Activity_Training";
        // permissions
    public static final int BLUETOOTH_PERMISSION_ENABLE = 1000;
    public static final int PERMISSION_COARSE_LOCATION =2000;
    // Bluetooth communication
    public static final int STATE_LISTENING =3000;
    public static final int STATE_CONNECTING =4000;
    public static final int STATE_CONNECTED =5000;
    public static final int STATE_CONNECTION_FAILED =6000;
    public static final int STATE_MESSAGE_RECIEVED =7000;
    public static final int STATE_MESSAGE_SENT_TO_DEVICE =7001;

    public static final int STATE_SEND_MESSAGE_TO_ARDUINO =3100;

    public static final int ELECTRODE_1 = 1;
    public static final int ELECTRODE_2 = 16;
    public static final int ELECTRODE_3 = 128;
    public static final int ELECTRODE_4 = 256;
    public static final int ELECTRODE_5 = 32;
    public static final int ELECTRODE_6 = 512;
    public static final int ELECTRODE_7 = 64;
    public static final int ELECTRODE_8 = 4;
    public static final int ELECTRODE_9 = 2;
    public static final int ELECTRODE_10 = 8;
    public static final int ELECTRODE_11 = 2048;
    public static final int ELECTRODE_12 = 1024;
    public static final int ALL_ELECTRODES = 4095;
    public static  int FIRST_TEN_ELECTRODES = 4095;
//    public static final int FIRST_TEN_ELECTRODES = 1023;

    // prepare training and starttraining communication
    public static final String DURATION = "Duration";
    public static final String DURATION_OF_STIMULATION_MS = "Duration_of_stimulation";
    public static final String DURATION_OF_REST_TIME_MS = "Duration_of_rest_time";
    public static final String KEY_FOR_VALUE_ADD_CLIENT_TO_RECYCLER = "Code_Add_Client_To_Recycler";
    public static final int ADD_CLIENT_TO_RECYCLER=260;

    public static final int PROGRAM_BASIC_KEY =8;
    public static final String PROGRAM_BASIC = App.getmContext().getString(R.string.Basic);
    public static final String SUBPROGRAM_GET_STARTED= App.getmContext().getString(R.string.Get_Started);
    public static final String SUBPROGRAM_BASIC_1=App.getmContext().getString(R.string.Basic_1);
    public static final String SUBPROGRAM_BASIC_2=App.getmContext().getString(R.string.Basic_2);
    public static final String SUBPROGRAM_CONTINUOUS_1=App.getmContext().getString(R.string.Continuous_1);
    public static final String SUBPROGRAM_CONTINUOUS_2=App.getmContext().getString(R.string.Continuous_2);

    // program cardio
    public static final int PROGRAM_CARDIO_KEY =14;
    public static final String PROGRAM_CARDIO =App.getmContext().getString(R.string.Cardio);
    public static final String SUBPROGRAM_ENDURANCE_1=App.getmContext().getString(R.string.Endurance_1);
    public static final String SUBPROGRAM_ENDURANCE_2=App.getmContext().getString(R.string.Endurance_2);
    public static final String SUBPROGRAM_ENDURANCE_3=App.getmContext().getString(R.string.Endurance_3);

    // program strength
    public static final int PROGRAM_STRENGTH_KEY =18;
    public static final String PROGRAM_STRENGTH =App.getmContext().getString(R.string.Strength);
    public static final String SUBPROGRAM_STRENGTH_1=App.getmContext().getString(R.string.Strength_1);
    public static final String SUBPROGRAM_STRENGTH_2=App.getmContext().getString(R.string.Strength_2);

    // program fatburning
    public static final int PROGRAM_FATBURNING_KEY =21;
    public static final String PROGRAM_FATBURNING =App.getmContext().getString(R.string.Fat_burning);
    public static final String SUBPROGRAM_FATBURNING_1=App.getmContext().getString(R.string.Fatburning_1);
    public static final String SUBPROGRAM_FATBURNING_2=App.getmContext().getString(R.string.Fatburning_2);

    // program relaxing
    public static final int PROGRAM_RELAXING_KEY =24;
    public static final String PROGRAM_RELAXING =App.getmContext().getString(R.string.Relaxation);
    public static final String SUBPROGRAM_COOLDOWN_1=App.getmContext().getString(R.string.Cooldown_1);
    public static final String SUBPROGRAM_COOLDOWN_2=App.getmContext().getString(R.string.Cooldown_2);

    // program massage
    public static final int PROGRAM_MASSAGE_KEY =27;
    public static final String PROGRAM_MASSAGE =App.getmContext().getString(R.string.Massage);
    public static final String SUBPROGRAM_MASAGE_ORDINARY=App.getmContext().getString(R.string.Massage_1);
    public static final String SUBPROGRAM_ANTI_CELULITE=App.getmContext().getString(R.string.Anti_cellulite);

    public static final String CHOSEN_PROGRAM="CHOSEN_PROGRAM";
    public static final String CHOOSEN_CHECKBOXES = "CHOOSEN_CHECKBOXES";

       // Arduino String messages
    public static final String ARDUINO_CONFIGURE_IMPULSE = "<P";
    public static final String ARDUINO_START ="<S";
    public static final String ARDUINO_SET_UP_WORKOUT = "<C";
    public static final String ARDUINO_SET_UP_LEVEL = "<L";
    public static final String ARDUINO_END_OF_MESSAGE = ">";
    public static final String ARDUINO_DELIMITER = ";";
    public static final String ARDUINO_STOP_ELECTRODE = "<T";
    public static final String ARDUINO_RESET = "<R";

    public static final String INTENSITY="intensity";
    public static final String INTENSITY_EASY="easy";
    public static final String INTENSITY_MEDIUM="medium";
    public static final String INTENSITY_HARD="hard";

    public static final String ELECTRODE_NUMBER_KEY = "ELECTRODDE_NUMBER_KEY";

    public static final String SETUP_PROGRAM_KEY="Command_C_Key";
    public static final String SUBPROGRAM_SELECTION="Command_C_Key";

    public static final String SHARED_PREF_KEY ="Shared_preferences_key";
    public static final String SHARED_PREF_EMAIL ="Shared_preferences_email";
    public static final String SHARED_PREF_PASSWORD ="Shared_preferences_password";
    public static final int PICK_USER_PROFILE_IMAGE = 123;

    public static final String SAVE_TRAINING_SHARED_PREF_KEY = "Save_training_shared_pref_key";
    public static final String INTNENT_BYTE_ARRAY_ARDUINO_KEY = "Intent_byte_array_arduino_key";
    public static final String BUNDLE_SET_TRAINING_KEY = "Bundle_set_training_key";
    public static final String BUNDLE_SET_TRAINING_KEY_FOR_NEXT_FRAGMENT = "Bundle_set_training_key_for_next_fragment";
    public static final String BUNDLE_SET_TRAINING_CONNECTED_DEVICES = "Bundle_set_training_connected_Devices";
    public static final String TABLE_CLIENT_IDS = "Table_client_ids";

    public static final int REQUEST_IMAGE_CAPTURE = 1233;
    public static final String CHOOSEN_TRAINER_ID = "Choosen_trainer_id";
    public static final String BUNDLE_SET_TRAINER_DATA = "Bundle_set_trainer_data";
    public static final String ARDUINO_MESSAGE = "Arduino_message";
    public static final String DEVICE_BT_ADDRESS = "Device_BT_address";
    public static final String EDIT_TRAINER = "EDIT_TRAINER";
    public static final String EDIT_TRAINER_ID = "EDIT_TRAINER_ID";
    public static final String EDIT_CLIENT = "EDIT_CLIENT";
    public static final String EDIT_CLIENT_ID = "EDIT_CLIENT_ID";


    public static final String STATISTICS_TABLE_NAME = "Statistics_Table";

    public static final int CAMERA_REQUEST = 2011;
    public static final int WRITE_STORAGE_REQUEST = 2021;

}
