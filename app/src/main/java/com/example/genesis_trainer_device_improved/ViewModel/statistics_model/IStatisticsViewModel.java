package com.example.genesis_trainer_device_improved.ViewModel.statistics_model;

import com.example.genesis_trainer_device_improved.Entity.Statistics;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public interface IStatisticsViewModel {


    Maybe<List<Statistics>> getAllStatistics();
    Single<Long> saveStatistics( Statistics statistics);
    Single<Integer> deleteAllEntriesInStatisticsTable();
    Single<Integer> getStatisticsCount();
}
