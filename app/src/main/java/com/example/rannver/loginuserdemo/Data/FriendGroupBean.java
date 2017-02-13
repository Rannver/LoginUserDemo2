package com.example.rannver.loginuserdemo.Data;

/**
 * Created by Rannver on 2017/2/8.
 * 用于显示好友列表信息的内部类
 */

public class FriendGroupBean {

    private String head_image_path;
    private String friend_id;
    private String friend_name;
    private String friend_remark;
    private String friend_flag;  //普通好友1，特别关心2，被特别关心3
    private String friend_job;
    private String friend_age;
    private String friend_sex;


    public String getHead_image_path() {
        return head_image_path;
    }

    public void setHead_image_path(String head_image_path) {
        this.head_image_path = head_image_path;
    }

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_remark() {
        return friend_remark;
    }

    public void setFriend_remark(String friend_remark) {
        this.friend_remark = friend_remark;
    }

    public String getFriend_job() {
        return friend_job;
    }

    public void setFriend_job(String friend_job) {
        this.friend_job = friend_job;
    }

    public String getFriend_age() {
        return friend_age;
    }

    public void setFriend_age(String friend_age) {
        this.friend_age = friend_age;
    }

    public String getFriend_sex() {
        return friend_sex;
    }

    public void setFriend_sex(String friend_sex) {
        this.friend_sex = friend_sex;
    }

    public String getFriend_flag() {
        return friend_flag;
    }

    public void setFriend_flag(String friend_flag) {
        this.friend_flag = friend_flag;
    }
}
