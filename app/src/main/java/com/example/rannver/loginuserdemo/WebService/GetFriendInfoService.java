package com.example.rannver.loginuserdemo.WebService;

import com.example.rannver.loginuserdemo.Data.gsonBean.FriendGsonBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/22.
 */

public interface GetFriendInfoService {
    @POST("getFriendsInfo")
    Call<List<FriendGsonBean>>  getFriendList(@Query("id") int id);
}
