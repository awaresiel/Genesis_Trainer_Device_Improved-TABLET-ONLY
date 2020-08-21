package com.example.genesis_trainer_device_improved.ViewModel.training_settings_model;

import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ITrainingSettingsViewModel {


    Maybe<UserTrainingSettingsTable> loadTrainingSettingsByID(int clientsID);
    Single<Integer> updateUserTrainingStorage(UserTrainingSettingsTable settings);
    Single<Long> addUserTrainingSettingsTable( UserTrainingSettingsTable settings);
    Single<Integer> deleteUserTrainingSettingsTable( int settingsId);

}
