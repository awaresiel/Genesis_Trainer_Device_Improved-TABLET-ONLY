package com.example.genesis_trainer_device_improved.helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {


    public static void saveToSharedPreferencesInt (String nameOfPrefFile, String keyOfSavedValue, int valToSave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(keyOfSavedValue,valToSave);
        editor.apply();

    }

    public static int getFromSharedPreferencesInt(String nameOfPrefFile, String keyOfSavedValue, Context context){

         SharedPreferences preferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
         int value = preferences.getInt(keyOfSavedValue,-1);
         return value;
    }

    public static void saveToSharedPreferencesString (String nameOfPrefFile, String keyOfSavedValue, String valToSave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keyOfSavedValue,valToSave);
        editor.apply();

    }

    public static String getFromSharedPreferencesString(String nameOfPrefFile, String keyOfSavedValue, Context context){

        SharedPreferences preferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        String value = preferences.getString(keyOfSavedValue,null);
        return value;
    }

    public static void saveToSharedPreferencesBoolean (String nameOfPrefFile, String keyOfSavedValue, Boolean valToSave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyOfSavedValue,valToSave);
        editor.apply();

    }

    public static Boolean getFromSharedPreferencesBoolean(String nameOfPrefFile, String keyOfSavedValue, Context context){

        SharedPreferences preferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        Boolean value = preferences.getBoolean(keyOfSavedValue,false);
        return value;
    }

    public static void saveToSharedPreferencesLong (String nameOfPrefFile, String keyOfSavedValue, Long valToSave, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(keyOfSavedValue,valToSave);
        editor.apply();

    }


    public static Long getFromSharedPreferencesLong(String nameOfPrefFile, String keyOfSavedValue, Context context){

        SharedPreferences preferences = context.getSharedPreferences(nameOfPrefFile, Context.MODE_PRIVATE);
        Long value = preferences.getLong(keyOfSavedValue,-1);
        return value;
    }

    public static void RemoveFromSharedPreferences(Context context, String prefFileName, String key){
        SharedPreferences.Editor editor = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }

    public static void RemoveSharedPreferencesFile(Context context, String prefFileName){
       context.deleteSharedPreferences(prefFileName);
    }

    public static boolean checkIfFileExists(Context context, String prefFileName){
        SharedPreferences prefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        if (prefs.getAll().isEmpty()){
            return false;
        }
        return true;
    }

}
