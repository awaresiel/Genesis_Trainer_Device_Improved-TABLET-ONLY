package com.example.genesis_trainer_device_improved.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

//    @FormUrlEncoded
//    @POST("autorization.php")
//    Call<UserLogIn> sendloginInfo(@Field("login") String login,
//                                  @Field("password") String password,
//                                  @Field("imei") Long imei);
//                                  @Field("state") String state,
//                                  @Field("login_id") Integer id);

    @GET("autorization.php")
    Call<ServerResponse> sendloginInfo(@Query(value = "login") String login,
                                       @Query(value = "password") String password,
                                       @Query(value = "imei") String imei);



//    @GET("registration.php")
////    @POST("api/register")
//    Call<ServerResponse> registerUser(@Body UserLogIn userLogIn);


//    @FormUrlEncoded
    @GET("registration.php")
//    @POST("registration.php")
    Call<ServerResponse> registerUser(@Query(value = "login") String login,
                                      @Query(value = "password") String password,
                                      @Query(value = "imei") String imei);
//                                 @Field("state") String state,
//                                 @Field("login_id") Integer id);
//



}
