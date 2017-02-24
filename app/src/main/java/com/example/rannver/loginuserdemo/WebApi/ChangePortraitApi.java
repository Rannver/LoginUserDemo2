package com.example.rannver.loginuserdemo.WebApi;

import com.example.rannver.loginuserdemo.WebService.ChangePortraitService;

import retrofit2.Retrofit;

/**
 * Created by Rannver on 2017/2/24.
 */

public class ChangePortraitApi extends webApi {
    String url = "http://youyinnn.cn/FamilyAssistant/us/";
    Retrofit retrofit = getApi(url);
    @Override
    public <T> T getService() {
        return (T) retrofit.create(ChangePortraitService.class);
    }
}
