package com.example.genesis_trainer_device_improved.Programs;

import android.util.Log;


import com.example.genesis_trainer_device_improved.helpers.Constants;
import com.example.genesis_trainer_device_improved.helpers.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

/*
this class contains lists of programs
there are 6 programs and 16 subprograms
 */

public class Programs {
    private static final String TAG = "Programs";

    public List<String> getProgramsList(){
        ArrayList<String> list = new ArrayList<>();
        list.add(Constants.PROGRAM_BASIC);
        list.add(Constants.PROGRAM_CARDIO);
        list.add(Constants.PROGRAM_STRENGTH);
        list.add(Constants.PROGRAM_FATBURNING);
        list.add(Constants.PROGRAM_RELAXING);
        list.add(Constants.PROGRAM_MASSAGE);

       // String str2=  LocaleHelper.getLanguage(App.getmContext());
       // Log.d(TAG, "getProgramsList: " + str2);
       return list;
    }

    public String[] getBasic_subprograms() {

        return new String[] {Constants.SUBPROGRAM_GET_STARTED, Constants.SUBPROGRAM_BASIC_1,Constants.SUBPROGRAM_BASIC_2, Constants.SUBPROGRAM_CONTINUOUS_1,Constants.SUBPROGRAM_CONTINUOUS_2};
//        return new String[] {App.SUBPROGRAM_GET_STARTED, App.SUBPROGRAM_BASIC_1,App.SUBPROGRAM_BASIC_2, App.SUBPROGRAM_CONTINUOUS_1,App.SUBPROGRAM_CONTINUOUS_2};

    }

    public String[] getCardioSubPrograms()
    {
        return new String[]{Constants.SUBPROGRAM_ENDURANCE_1, Constants.SUBPROGRAM_ENDURANCE_2,Constants.SUBPROGRAM_ENDURANCE_3};
//        return new String[]{App.SUBPROGRAM_ENDURANCE_1, App.SUBPROGRAM_ENDURANCE_2,App.SUBPROGRAM_ENDURANCE_3};
    }

    public String[] getStrengthSubPrograms()
    {
        return new String[]{Constants.SUBPROGRAM_STRENGTH_1,Constants.SUBPROGRAM_STRENGTH_2};
//        return new String[]{App.SUBPROGRAM_STRENGTH_1,App.SUBPROGRAM_STRENGTH_2};
    }
    public String[] getFatBurningSubPrograms()
    {
        return new String[]{Constants.SUBPROGRAM_FATBURNING_1, Constants.SUBPROGRAM_FATBURNING_2};
//        return new String[]{App.SUBPROGRAM_FATBURNING_1, App.SUBPROGRAM_FATBURNING_2};
    }

    public String[] getRelaxationSubPrograms()
    {
        return new String[]{Constants.SUBPROGRAM_COOLDOWN_1, Constants.SUBPROGRAM_COOLDOWN_2};
//        return new String[]{App.SUBPROGRAM_COOLDOWN_1, App.SUBPROGRAM_COOLDOWN_2};
    }

    public String[] getMassageSubPrograms()
    {
        return new String[]{Constants.PROGRAM_MASSAGE, Constants.SUBPROGRAM_ANTI_CELULITE};
//        return new String[]{App.PROGRAM_MASSAGE, App.SUBPROGRAM_ANTI_CELULITE};
    }
}













