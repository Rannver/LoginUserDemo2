package com.example.rannver.loginuserdemo.WebApi;

import com.example.rannver.loginuserdemo.WebService.AddFriendService;
import com.example.rannver.loginuserdemo.WebService.DeleteFriendService;

import retrofit2.Retrofit;

/**
 * Created by Rannver on 2017/2/21.
 */

public class DeleteFriendApi extends webApi {
    String url = "http://youyinnn.cn/FamilyAssistant/fs/";
    Retrofit retrofit = getApi(url);

    @Override
    public <T> T getService() {
        return (T) retrofit.create(DeleteFriendService.class);
    }
}
