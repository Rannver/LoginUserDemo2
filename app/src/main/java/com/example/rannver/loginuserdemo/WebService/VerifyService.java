package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface VerifyService {
    @POST("verify")
    Call<String> getState(@Query("username") String name);
}
