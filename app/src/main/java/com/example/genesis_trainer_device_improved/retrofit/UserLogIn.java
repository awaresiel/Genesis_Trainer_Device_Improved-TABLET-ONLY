package com.example.genesis_trainer_device_improved.retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogIn {
    private static final String TAG = UserLogIn.class.getSimpleName();

    @SerializedName("imei")
    @Expose
    private String imei;

    @SerializedName("login")
//    @SerializedName("email")
    @Expose
    private String login;


    @SerializedName("password")
    @Expose
    private String password;

   private String[] device_list;


    public UserLogIn(String email, String Password, String imei) {
        this.login = email;
        this.password = Password;
        this.imei = imei;

        String s = new Gson().toJson(email);
        Log.d(TAG, "UserLogIn: s= " + s);
        String s2 = new Gson().toJson(password);
        Log.d(TAG, "UserLogIn: s1= " + s2);
        String s3 = new Gson().toJson(imei);
        Log.d(TAG, "UserLogIn: s2= " + s3);
    }


    public String getImei() {
        return imei.toString();
    }


    public String getLogin() {

        return login;
    }

    public String getPassword() {
        return password;
    }


//    @NonNull
//    @Override
//    public String toString() {
//        return "UserLogin" +
////                " id= " + login_id +"\n"+
//                " email= "+ login +"\n"+
//                " imei= " + imei +"\n"+
//                " password= " + password +"\n";
//    }
}
