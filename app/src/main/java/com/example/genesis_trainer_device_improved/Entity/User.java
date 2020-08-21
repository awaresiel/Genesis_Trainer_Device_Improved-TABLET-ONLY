package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import com.example.genesis_trainer_device_improved.helpers.Constants;

import java.io.Serializable;


//@Entity(tableName = Constants.TABLE_NAME, indices = {@Index(value = {"UserEmail"}, unique = true)})
@Entity(tableName = Constants.TABLE_NAME)
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "UserId")
    private int userId;
    @NonNull
    @ColumnInfo(name = "UserName")
    private String userName;
    @NonNull
    @ColumnInfo(name = "UserSurname")
    private String userSurname;
    @NonNull
    @ColumnInfo(name = "UserEmail")
    private String userEmail;
    @NonNull
    @ColumnInfo(name = "UserPhone")
    private String userPhone;
    @NonNull
    @ColumnInfo(name = "UserAddress")
    private String userAddress;
//        @NonNull
    @ColumnInfo(name = "UserProfileImage")
    private String userProfileImage;
    @NonNull
    @ColumnInfo(name = "UserBirthday")
    private String userBirthday;
    @NonNull
    @ColumnInfo(name = "UserHeight")
    private int userHeight;
    @NonNull
    @ColumnInfo(name = "UserWeight")
    private int userWeight;
    @NonNull
    @ColumnInfo(name = "UserGender")
    private String userGender;
    // @NonNull
    @ColumnInfo(name = "UserIsTrainer")
    private boolean userIsTrainer;
    @NonNull
    @ColumnInfo(name = "UserPostalCode")
    private int userPostalCode;
    // @NonNull
    @ColumnInfo(name = "UserNotes")
    private String userNotes;
    @NonNull
    @ColumnInfo(name = "UserPassword")
    private String userPassword;


    public User() {

    }

    // Getters and Setters for createing User


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(@NonNull String userSurname) {
        this.userSurname = userSurname;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }

    @NonNull
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(@NonNull String userPhone) {
        this.userPhone = userPhone;
    }

    @NonNull
    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(@NonNull String userAddress) {
        this.userAddress = userAddress;
    }

    @NonNull
    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(@NonNull String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    @NonNull
    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(@NonNull String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    @NonNull
    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(@NonNull String userGender) {
        this.userGender = userGender;
    }

    public boolean isUserIsTrainer() {
        return userIsTrainer;
    }

    public void setUserIsTrainer(boolean userIsTrainer) {
        this.userIsTrainer = userIsTrainer;
    }

    public int getUserPostalCode() {
        return userPostalCode;
    }

    public void setUserPostalCode(int userPostalCode) {
        this.userPostalCode = userPostalCode;
    }

    @NonNull
    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(@NonNull String userNotes) {
        this.userNotes = userNotes;
    }

    @NonNull
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(@NonNull String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean hasImage() {

        if (!userProfileImage.isEmpty()) {
            return true;
        }
        return false;

    }
}
