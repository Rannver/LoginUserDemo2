package com.example.rannver.loginuserdemo.WebService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/24.
 */

public interface ChangePortraitService  {
    @Multipart
    @POST("cgPortrait")
    Call<String>  getState(@Query("username") String name,
                           @Part MultipartBody.Part file);
}
