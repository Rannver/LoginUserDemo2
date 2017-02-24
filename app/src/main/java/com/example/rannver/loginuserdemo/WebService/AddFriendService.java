package com.example.rannver.loginuserdemo.WebService;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Rannver on 2017/2/21.
 */

public interface AddFriendService {
    @POST("addFriend")
    Call<String> getState(@Query("u_id") int user_id,
                          @Query("f_id") int friend_id);
}
