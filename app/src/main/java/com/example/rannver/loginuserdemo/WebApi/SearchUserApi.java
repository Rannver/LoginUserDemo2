package com.example.rannver.loginuserdemo.WebApi;

import com.example.rannver.loginuserdemo.WebService.SearchUserService;

import retrofit2.Retrofit;

/**
 * Created by Rannver on 2017/2/21.
 */

public class SearchUserApi extends webApi {

    String url = "http://youyinnn.cn/FamilyAssistant/fs/";
    Retrofit retrofit = getApi(url);

    @Override
    public <T> T getService() {
        return (T) retrofit.create(SearchUserService.class);
    }
}
