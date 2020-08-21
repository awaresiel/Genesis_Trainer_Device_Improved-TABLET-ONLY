package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.io.Serializable;

@Entity(tableName = Constants.TABLE_TRAINER_NAME)
public class Trainer implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "TrainerId")
    private int trainerId;
    @NonNull
    @ColumnInfo(name = "TrainerName")
    private String trainerName;
//    @NonNull
    @ColumnInfo(name = "TrainerSurname")
    private String trainerSurname;
//    @NonNull
    @ColumnInfo(name = "TrainerEmail")
    private String trainerEmail;
//    @NonNull
    @ColumnInfo(name = "TrainerPhone")
    private String trainerPhone;
//    @NonNull
    @ColumnInfo(name = "TrainerAddress")
    private String trainerAddress;
    //        @NonNull
    @ColumnInfo(name = "TrainerProfileImage")
    private String trainerProfileImage;
//    @NonNull
    @ColumnInfo(name = "TrainerBirthday")
    private String trainerBirthday;
//    @NonNull
    @ColumnInfo(name = "TrainerHeight")
    private int trainerHeight;
//    @NonNull
    @ColumnInfo(name = "TrainerWeight")
    private int trainerWeight;
//    @NonNull
    @ColumnInfo(name = "TrainerGender")
    private String trainerGender;

    // @NonNull
    @ColumnInfo(name = "TrainerNotes")
    private String trainerNotes;



    public Trainer() {

    }

    public int getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(int trainerId) {
        this.trainerId = trainerId;
    }

    @NonNull
    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(@NonNull String trainerName) {
        this.trainerName = trainerName;
    }

//    @NonNull
    public String getTrainerSurname() {
        return trainerSurname;
    }

    public void setTrainerSurname(String trainerSurname) {
        this.trainerSurname = trainerSurname;
    }

//    @NonNull
    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail( String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

//    @NonNull
    public String getTrainerPhone() {
        return trainerPhone;
    }

    public void setTrainerPhone( String trainerPhone) {
        this.trainerPhone = trainerPhone;
    }

//    @NonNull
    public String getTrainerAddress() {
        return trainerAddress;
    }

    public void setTrainerAddress( String trainerAddress) {
        this.trainerAddress = trainerAddress;
    }

    public String getTrainerProfileImage() {
        return trainerProfileImage;
    }

    public void setTrainerProfileImage(String trainerProfileImage) {
        this.trainerProfileImage = trainerProfileImage;
    }


    public String getTrainerBirthday() {
        return trainerBirthday;
    }

    public void setTrainerBirthday( String trainerBirthday) {
        this.trainerBirthday = trainerBirthday;
    }

    public int getTrainerHeight() {
        return trainerHeight;
    }

    public void setTrainerHeight(int trainerHeight) {
        this.trainerHeight = trainerHeight;
    }

    public int getTrainerWeight() {
        return trainerWeight;
    }

    public void setTrainerWeight(int trainerWeight) {
        this.trainerWeight = trainerWeight;
    }


    public String getTrainerGender() {
        return trainerGender;
    }

    public void setTrainerGender( String trainerGender) {
        this.trainerGender = trainerGender;
    }

    public String getTrainerNotes() {
        return trainerNotes;
    }

    public void setTrainerNotes(String trainerNotes) {
        this.trainerNotes = trainerNotes;
    }
}
