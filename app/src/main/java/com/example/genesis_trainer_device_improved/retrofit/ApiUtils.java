package com.example.genesis_trainer_device_improved.retrofit;


public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://genesis-ems.ru/control_access/";
//    public static final String BASE_URL = "https://reqres.in/";

    public static APIService getAPIService() {

    return RetrofitClient.getRetrofit(BASE_URL).create(APIService.class);

    }
}
