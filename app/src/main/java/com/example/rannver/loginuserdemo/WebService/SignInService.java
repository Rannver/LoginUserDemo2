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
    Call<String> getState(@Part("username") RequestBody name,
                          @Part("password") RequestBody pwd,
                          @Part("gender")   RequestBody sex,
                          @Part("birthday") RequestBody birthday,
                          @Part("address") RequestBody address,
                          @Part("career") RequestBody job,
                          @Part("phone_number") RequestBody phone,
                          @Part MultipartBody.Part file);
}
