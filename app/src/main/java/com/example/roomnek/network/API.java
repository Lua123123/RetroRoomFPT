package com.example.roomnek.network;

import com.example.roomnek.model.Topic;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    public static final String DOMAIN = "http://172.25.48.1/quanlytuvung/public/api/";
    API api = new Retrofit.Builder()
            .baseUrl(DOMAIN) // API base url
            .addConverterFactory(GsonConverterFactory.create()) // Factory phụ thuộc vào format JSON trả về
            .build()
            .create(API.class);

    @FormUrlEncoded
    @POST("topic/getAllTopic")
    Call<Topic> getTopics(@Field("user_create") int user_create);


}
