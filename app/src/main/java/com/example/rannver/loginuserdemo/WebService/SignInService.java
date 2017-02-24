package com.example.rannver.loginuserdemo.WebService;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface SignInService {
    @Multipart
    @POST("signIn")
    Call<String> getState(@Query("username") String name,
                          @Query("password") String pwd,
                          @Query("gender") String sex,
                          @Query("birthday") long birthday,
                          @Query("address") String address,
                          @Query("career") String job,
                          @Query("phone_number") long phone,
                          @Part MultipartBody.Part file);
}
