package com.example.rannver.loginuserdemo.WebService;

import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface LoginService {
    @POST("login")

    Call<PersonInfoGsonBean>  getState(@Query("username") String name,
                                       @Query("password") String pwd);

}
