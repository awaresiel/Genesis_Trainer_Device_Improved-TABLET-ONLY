package com.example.genesis_trainer_device_improved.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";
    private static Retrofit retrofit = null;

//    private static OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();

    public static Retrofit getRetrofit(String url){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create()) .build();
//.client(httpClient.build())


        }
        return retrofit;
    }
}
