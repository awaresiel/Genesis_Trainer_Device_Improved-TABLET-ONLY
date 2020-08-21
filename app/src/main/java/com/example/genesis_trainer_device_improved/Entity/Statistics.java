package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.genesis_trainer_device_improved.helpers.Constants;


@Entity(tableName = Constants.STATISTICS_TABLE_NAME)
public class Statistics {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "StatisticsID")
    int id;
    @NonNull
    @ColumnInfo(name = "StatisticsName")
    String name;
    @NonNull
    @ColumnInfo(name = "trainingName")
    String trainingName;
    @NonNull
    @ColumnInfo(name = "trainingDuration")
    String duration;
    @NonNull
    @ColumnInfo(name = "trainerName")
    String trainerName;
    @NonNull
    @ColumnInfo(name = "trainingDate")
    String date;

    public Statistics(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(@NonNull String trainingName) {
        this.trainingName = trainingName;
    }

    @NonNull
    public String getDuration() {
        return duration;
    }

    public void setDuration(@NonNull String duration) {
        this.duration = duration;
    }

    @NonNull
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(@NonNull String trainerName) {
        this.trainerName = trainerName;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
