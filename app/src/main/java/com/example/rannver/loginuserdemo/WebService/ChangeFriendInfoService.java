package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/23.
 */

public interface ChangeFriendInfoService {
    @POST("changeFriendInfo")
    Call<String>  getState(@Query("id") int user_id,
                           @Query("friend_id") int friend_id,
                           @Query("relationship") String relation,
                           @Query("remark") String remark);
}
