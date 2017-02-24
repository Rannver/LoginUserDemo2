package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/24.
 */

public interface RemoveCareService {
    @POST("removeCare")
    Call<String> getState(@Query("id") int id);
}
