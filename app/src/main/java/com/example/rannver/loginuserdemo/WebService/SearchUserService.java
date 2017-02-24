package com.example.rannver.loginuserdemo.WebService;

import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface SearchUserService {
    @POST("searchUser")
    Call<PersonInfoGsonBean> getList(@Query("username") String name);
}
