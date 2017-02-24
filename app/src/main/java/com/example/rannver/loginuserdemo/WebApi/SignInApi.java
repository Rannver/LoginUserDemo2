package com.example.rannver.loginuserdemo.WebApi;

import com.example.rannver.loginuserdemo.WebService.SignInService;

import retrofit2.Retrofit;

/**
 * Created by Rannver on 2017/2/21.
 */

public class SignInApi extends webApi {
    String url = "http://youyinnn.cn/FamilyAssistant/lg/";
    Retrofit retrofit = getApi(url);
    @Override
    public <T> T getService() {
        return (T) retrofit.create(SignInService.class);
    }
}
