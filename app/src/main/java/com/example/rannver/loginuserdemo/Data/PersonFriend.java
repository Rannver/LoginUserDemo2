package com.example.rannver.loginuserdemo.Data;

import org.litepal.crud.DataSupport;

/**
 * Created by Rannver on 2017/2/3.
 * 用户好友信息表
 */

public class PersonFriend extends DataSupport{

    private int friend_id;
    private String friend_relationship;
    private String friend_remark;

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_relation() {
        return friend_relationship;
    }

    public void setFriend_relation(String friend_relationship) {
        this.friend_relationship = friend_relationship;
    }

    public String getFriend_remark() {
        return friend_remark;
    }

    public void setFriend_remark(String friend_remark) {
        this.friend_remark = friend_remark;
    }
}
