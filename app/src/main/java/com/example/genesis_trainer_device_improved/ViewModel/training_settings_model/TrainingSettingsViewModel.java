package com.example.genesis_trainer_device_improved.ViewModel.training_settings_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.genesis_trainer_device_improved.Entity.UserTrainingSettingsTable;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrainingSettingsViewModel extends BaseViewModel implements ITrainingSettingsViewModel {
    private static final String TAG = "TrainingSettingsViewMod";


    private MutableLiveData<UserTrainingSettingsTable> table;

    public TrainingSettingsViewModel(@NonNull Application application) {
        super(application);
        table = new MutableLiveData<>();
    }

    public LiveData<UserTrainingSettingsTable> getTable() {
        return table;
    }

    public void setTable(UserTrainingSettingsTable table) {
        this.table.postValue(table);
    }

    @Override
    public Single<Integer> updateUserTrainingStorage(UserTrainingSettingsTable settings) {

        return userDAO.updateUserTrainingStorage(settings).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Long> addUserTrainingSettingsTable(UserTrainingSettingsTable settings) {

        return userDAO.addUserTrainingSettingsTable(settings).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


    }

    @Override
    public Single<Integer> deleteUserTrainingSettingsTable(int settingsId) {

        return userDAO.deleteUserTrainingSettingsTable(settingsId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

                }




    @Override
    public Maybe<UserTrainingSettingsTable> loadTrainingSettingsByID(int clientsID) {

        return userDAO.getUserTrainingSettingTableByClientsId(clientsID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }



}
