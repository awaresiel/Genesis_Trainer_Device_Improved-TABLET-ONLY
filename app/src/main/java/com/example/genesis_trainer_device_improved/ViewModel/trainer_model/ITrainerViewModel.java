package com.example.genesis_trainer_device_improved.ViewModel.trainer_model;

import androidx.lifecycle.LiveData;

import com.example.genesis_trainer_device_improved.Entity.Client;
import com.example.genesis_trainer_device_improved.Entity.Trainer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ITrainerViewModel {


    Single<Integer> DeleteTrainer(int trainerID);
    Single<Integer> updateTrainer( Trainer trainer);
    Single<Long> insertTrainer( Trainer trainer);
    Maybe<Trainer> loadTrainerById(int ID);
    Flowable<List<Trainer>> loadAllTrainers();
    void setTrainers(List<Trainer> c);
    void setTrainer(Trainer c);
    LiveData<Trainer> getTrainer();
    LiveData<List<Trainer>> getTrainers();
    Single<Integer> getTrainersCount();
}
