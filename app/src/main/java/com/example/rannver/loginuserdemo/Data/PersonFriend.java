package com.example.rannver.loginuserdemo.Data;

import org.litepal.crud.DataSupport;

/**
 * Created by Rannver on 2017/2/3.
 * 用户好友信息表
 */

public class PersonFriend extends DataSupport{

    private int user_id;
    private int friend_id;
    private int friend_flag;  //用于判断是否是特殊关心或被特殊关心的好友的flag,普通好友1，特别关心谁2，被谁特别关心3
    private String friend_relation;
    private String friend_remark;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public int getFriend_flag() {
        return friend_flag;
    }

    public void setFriend_flag(int friend_flag) {
        this.friend_flag = friend_flag;
    }

    public String getFriend_relation() {
        return friend_relation;
    }

    public void setFriend_relation(String friend_relation) {
        this.friend_relation = friend_relation;
    }

    public String getFriend_remark() {
        return friend_remark;
    }

    public void setFriend_remark(String friend_remark) {
        this.friend_remark = friend_remark;
    }
}
