package com.example.genesis_trainer_device_improved.ViewModel.statistics_model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.genesis_trainer_device_improved.Entity.Statistics;
import com.example.genesis_trainer_device_improved.ViewModel.BaseViewModel;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StatisticsViewModel extends BaseViewModel implements IStatisticsViewModel {
    private static final String TAG = "StatisticsViewModel";


    public StatisticsViewModel(@NonNull Application application) {
        super(application);
    }


    @Override
    public Maybe<List<Statistics>> getAllStatistics() {

        return userDAO.getAllStatistics().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Single<Long> saveStatistics(Statistics statistics) {

        return userDAO.saveStatistics(statistics).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> deleteAllEntriesInStatisticsTable() {

        return userDAO.deleteAllStatisticsEntries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<Integer> getStatisticsCount() {

        return userDAO.getStatisticsCount().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
