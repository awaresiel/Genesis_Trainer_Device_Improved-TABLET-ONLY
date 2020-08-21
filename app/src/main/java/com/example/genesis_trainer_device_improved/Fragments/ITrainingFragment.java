package com.example.genesis_trainer_device_improved.Fragments;

import android.os.Bundle;

import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;

public interface ITrainingFragment {
    UserTrainingSettingsTable getCurrentTraining();
    Bundle saveTimerStates();
    void setTextForGroupTrainingSuits();
    void setTextForPersonalSuit();
    void loadTraining();

}
