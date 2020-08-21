package com.example.genesis_trainer_device_improved.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.genesis_trainer_device_improved.helpers.Constants;



/*
 * this class is here in order to save checkbox states and seekbar levels
 */

@Entity(tableName = Constants.TABLE_CLIENT_SETTING)
public class UserTrainingSettingsTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ClientId")
   private int clientId;
    @NonNull
    @ColumnInfo(name = "trainingFrequency")
    private byte[] trainingFrequency;
    @NonNull
    @ColumnInfo(name = "trainingSetup")
    private  byte[] trainingSetup;
    @NonNull
    @ColumnInfo(name = "timeOfStimulation_ms")
    public int timeOfStimulation_ms = 0;
    @NonNull
    @ColumnInfo(name = "duration")
    public int duration=0;
    @NonNull
    @ColumnInfo(name = "restTime_ms")
    public int restTime_ms = 0;
    @NonNull
    @ColumnInfo(name = "whichElectrodes")
    public int whichElectrodes=0;
    @NonNull
    @ColumnInfo(name = "trainingName")
    private String trainingName;
    @NonNull
    @ColumnInfo(name = "chest")
    private boolean chest;
    @NonNull
    @ColumnInfo(name = "chestLevel")
    private int chestLevel;
    @NonNull
    @ColumnInfo(name = "hands")
    private boolean hands;
    @NonNull
    @ColumnInfo(name = "handsLevel")
    private int handsLevel;
    @NonNull
    @ColumnInfo(name = "abdomen")
    private boolean abdomen;
    @NonNull
    @ColumnInfo(name = "abdomenLevel")
    private int abdomenLevel;
    @NonNull
    @ColumnInfo(name = "quadriceps")
    private boolean quadriceps;
    @NonNull
    @ColumnInfo(name = "quadricepsLevel")
    private int quadricepsLevel;
    @NonNull
    @ColumnInfo(name = "trapez")
    private boolean trapez;
    @NonNull
    @ColumnInfo(name = "trapezLevel")
    private int trapezLevel;
    @NonNull
    @ColumnInfo(name = "back")
    private boolean back;
    @NonNull
    @ColumnInfo(name = "backLevel")
    private int backLevel;
    @NonNull
    @ColumnInfo(name = "lowerBack")
    private boolean lowerBack;
    @NonNull
    @ColumnInfo(name = "lowerBackLevel")
    private int lowerBackLevel;
    @NonNull
    @ColumnInfo(name = "hamstrings")
    private boolean hamstrings;
    @NonNull
    @ColumnInfo(name = "hamstringsLevel")
    private int hamstringsLevel;
    @NonNull
    @ColumnInfo(name = "glutes")
    private boolean glutes;
    @NonNull
    @ColumnInfo(name = "glutesLevel")
    private int glutesLevel;
    @NonNull
    @ColumnInfo(name = "extra")
    private boolean extra;
    @NonNull
    @ColumnInfo(name = "extraLevel")
    private int extraLevel;
    @NonNull
    @ColumnInfo(name = "triceps")
    private boolean triceps;
    @NonNull
    @ColumnInfo(name = "tricepsLevel")
    private int tricepsLevel;
    @NonNull
    @ColumnInfo(name = "shoulder")
    private boolean shoulder;
    @NonNull
    @ColumnInfo(name = "shoulderLevel")
    private int shoulderLevel;
    @NonNull
    @ColumnInfo(name = "IncreaseAllLevelsLevel")
    private int IncreaseAllLevelsLevel;

    @NonNull
    @ColumnInfo(name = "intensity")
    private String intensity;


    @ColumnInfo(name = "btAddress")
    private String btAddress;


    public UserTrainingSettingsTable() {

    }

    public String getBtAddress() {
        return btAddress;
    }

    public void setBtAddress(@NonNull String btAddress) {
        this.btAddress = btAddress;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public byte[] getTrainingFrequency() {
        return trainingFrequency;
    }

    public void setTrainingFrequency(@NonNull byte[] trainingFrequency) {
        this.trainingFrequency = trainingFrequency;
    }

    public byte[] getTrainingSetup() {
        return trainingSetup;
    }

    public void setTrainingSetup(@NonNull byte[] trainingSetup) {
        this.trainingSetup = trainingSetup;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(@NonNull String trainingName) {
        this.trainingName = trainingName;
    }

    public boolean isChest() {
        return chest;
    }

    public void setChest(boolean chest) {
        this.chest = chest;
    }

    public boolean isHands() {
        return hands;
    }

    public void setHands(boolean hands) {
        this.hands = hands;
    }

    public boolean isAbdomen() {
        return abdomen;
    }

    public void setAbdomen(boolean abdomen) {
        this.abdomen = abdomen;
    }

    public boolean isQuadriceps() {
        return quadriceps;
    }

    public void setQuadriceps(boolean quadriceps) {
        this.quadriceps = quadriceps;
    }

    public boolean isTrapez() {
        return trapez;
    }

    public void setTrapez(boolean trapez) {
        this.trapez = trapez;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public boolean isLowerBack() {
        return lowerBack;
    }

    public void setLowerBack(boolean lowerBack) {
        this.lowerBack = lowerBack;
    }

    public boolean isHamstrings() {
        return hamstrings;
    }

    public void setHamstrings(boolean hamstrings) {
        this.hamstrings = hamstrings;
    }

    public boolean isGlutes() {
        return glutes;
    }

    public void setGlutes(boolean glutes) {
        this.glutes = glutes;
    }

    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public boolean isTriceps() {
        return triceps;
    }

    public void setTriceps(boolean triceps) {
        this.triceps = triceps;
    }

    public boolean isShoulder() {
        return shoulder;
    }

    public void setShoulder(boolean shoulder) {
        this.shoulder = shoulder;
    }

    public int getChestLevel() {
        return chestLevel;
    }

    public void setChestLevel(int chestLevel) {
        this.chestLevel = chestLevel;
    }

    public int getHandsLevel() {
        return handsLevel;
    }

    public void setHandsLevel(int handsLevel) {
        this.handsLevel = handsLevel;
    }

    public int getAbdomenLevel() {
        return abdomenLevel;
    }

    public void setAbdomenLevel(int abdomenLevel) {
        this.abdomenLevel = abdomenLevel;
    }

    public int getQuadricepsLevel() {
        return quadricepsLevel;
    }

    public void setQuadricepsLevel(int quadricepsLevel) {
        this.quadricepsLevel = quadricepsLevel;
    }

    public int getTrapezLevel() {
        return trapezLevel;
    }

    public void setTrapezLevel(int trapezLevel) {
        this.trapezLevel = trapezLevel;
    }

    public int getBackLevel() {
        return backLevel;
    }

    public void setBackLevel(int backLevel) {
        this.backLevel = backLevel;
    }

    public int getLowerBackLevel() {
        return lowerBackLevel;
    }

    public void setLowerBackLevel(int lowerBackLevel) {
        this.lowerBackLevel = lowerBackLevel;
    }

    public int getHamstringsLevel() {
        return hamstringsLevel;
    }

    public void setHamstringsLevel(int hamstringsLevel) {
        this.hamstringsLevel = hamstringsLevel;
    }

    public int getGlutesLevel() {
        return glutesLevel;
    }

    public void setGlutesLevel(int glutesLevel) {
        this.glutesLevel = glutesLevel;
    }

    public int getExtraLevel() {
        return extraLevel;
    }

    public void setExtraLevel(int extraLevel) {
        this.extraLevel = extraLevel;
    }

    public int getTricepsLevel() {
        return tricepsLevel;
    }

    public void setTricepsLevel(int tricepsLevel) {
        this.tricepsLevel = tricepsLevel;
    }

    public int getShoulderLevel() {
        return shoulderLevel;
    }

    public void setShoulderLevel(int shoulderLevel) {
        this.shoulderLevel = shoulderLevel;
    }

    public int getIncreaseAllLevelsLevel() {
        return IncreaseAllLevelsLevel;
    }

    public void setIncreaseAllLevelsLevel(int increaseAllLevelsLevel) {
        IncreaseAllLevelsLevel = increaseAllLevelsLevel;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "\n clientId = " + clientId +
//                "trainingFrequency = " + trainingFrequency+
//                "trainingSetup = " +  trainingSetup+
                "\n timeOfStimulation_ms = " +  timeOfStimulation_ms+
                "\n duration = " + duration+
                "\n restTime_ms = " + restTime_ms+
                "\n whichElectrodes = " + whichElectrodes+
                "\n trainingName = " + trainingName+
                "\n chest = " + chest+
                "\n chestLevel = " + chestLevel+
                "\n hands = " + hands+
                "\n handsLevel = " +  handsLevel+
                "\n abdomen = " +   abdomen+
                "\n abdomenLevel = " + abdomenLevel+
                "\n quadriceps = " +   quadriceps+
                "\n quadricepsLevel = " +   quadricepsLevel+
                "\n trapez = " +    trapez+
                "\n trapezLevel = " + trapezLevel+
                "\n back = " +  back+
                "\n backLevel = " + backLevel+
                "\n lowerBack = " + lowerBack+
                "\n lowerBackLevel = " + lowerBackLevel+
                "\n hamstrings = " +  hamstrings+
                "\n hamstringsLevel = " + hamstringsLevel+
                "\n glutes = " +glutes+
                "\n glutesLevel = " +   glutesLevel+
                "\n extra = " + extra+
                "\n extraLevel = " + extraLevel+
                "\n triceps = " + triceps+
                "\n tricepsLevel = " + tricepsLevel+
                "\n shoulder = " + shoulder+
                "\n shoulderLevel = " + shoulderLevel+
                "\n IncreaseAllLevelsLevel = " + IncreaseAllLevelsLevel+
                "\n intensity = " + intensity +
                "\n btAddress = " + btAddress;




        return str;
    }
}
