package com.example.javachat.Fragments;


import com.example.javachat.Notifications.MyResponse;
import com.example.javachat.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAALpl6bbM:APA91bFa_XZhs7Avj6dMc_zZcMpZ3VUWMD0yPJbnR_JaMLJyGRpyH7hNhk3ZmGqAFgNa_V8x-Iu3zt0oGGBBgu7ZT4mS7vSe5dgT-1TfDUSCfK2l3Z7k0RQgcJqYbUbXSTm1e6A2vSqs"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
