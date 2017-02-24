package com.example.rannver.loginuserdemo.WebApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rannver on 2017/2/21.
 */

public abstract class webApi {
    Retrofit getApi(String url){
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public abstract <T> T getService();
}
