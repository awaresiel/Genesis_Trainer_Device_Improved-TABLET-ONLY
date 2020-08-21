package com.example.genesis_trainer_device_improved.ViewModel.trainer_model;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Trainer;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TrainerViewModel extends BaseViewModel implements ITrainerViewModel {
    private static final String TAG = "TrainerViewModel";

    private MutableLiveData<Trainer> trainer;
    private MutableLiveData<List<Trainer>> trainers;

    public TrainerViewModel(@NonNull Application application) {
        super(application);
        trainer = new MutableLiveData<>();
        trainers = new MutableLiveData<>();
    }

    @Override
    public LiveData<Trainer> getTrainer() {
        return trainer;
    }

    @Override
    public LiveData<List<Trainer>> getTrainers() {
        return trainers;
    }

    @Override
    public void setTrainer(Trainer trainer) {
        this.trainer.postValue(trainer);
    }

    @Override
    public void setTrainers(List<Trainer> trainers) {
        this.trainers.postValue(trainers);
    }

    @Override
    public Single<Integer> DeleteTrainer(int trainerID) {

        return  userDAO.deleteTrainer(trainerID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> updateTrainer(Trainer trainer) {

          return userDAO.updateTrainer(trainer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Long> insertTrainer(Trainer trainer) {

              return userDAO.addTrainer(trainer).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Maybe<Trainer> loadTrainerById(int ID) {
       return userDAO.loadTrainerById(ID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Flowable<List<Trainer>> loadAllTrainers() {

        return  userDAO.loadAllTrainers().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public  Single<Integer> getTrainersCount() {

        return  userDAO.getTrainerssCount().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
