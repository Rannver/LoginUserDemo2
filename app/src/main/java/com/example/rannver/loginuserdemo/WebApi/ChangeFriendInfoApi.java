package com.example.rannver.loginuserdemo.WebApi;

import com.example.rannver.loginuserdemo.WebService.ChangeFriendInfoService;

import retrofit2.Retrofit;

/**
 * Created by Rannver on 2017/2/23.
 */

public class ChangeFriendInfoApi extends webApi {
    String url = "http://youyinnn.cn/FamilyAssistant/us/";
    Retrofit retrofit = getApi(url);
    @Override
    public <T> T getService() {
        return (T) retrofit.create(ChangeFriendInfoService.class);
    }
}
