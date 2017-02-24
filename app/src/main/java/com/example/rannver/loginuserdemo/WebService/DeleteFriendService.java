package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface DeleteFriendService {
    @POST("deleteFriend")
    Call<String> getState(@Query("u_id") String user_id,
                          @Query("f_id") String friend_id);
}
