package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/24.
 */

public interface ChangeUserInfoService {
    @POST("cgUserInfo")
    Call<String> getState(@Query("id") int id,
                          @Query("age") int age,
                          @Query("phone_number") long phone_number,
                          @Query("name") String username,
                          @Query("career") String job,
                          @Query("address") String address,
                          @Query("birthday") long birthday);
}
