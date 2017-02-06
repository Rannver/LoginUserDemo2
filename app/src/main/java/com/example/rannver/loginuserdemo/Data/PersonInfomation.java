package com.example.rannver.loginuserdemo.Data;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by Rannver on 2017/2/3.
 * 用户个人基本信息表
 */

public class PersonInfomation extends DataSupport{

    private int   user_id;
    private String user_name;
    private String user_pwd;
    private String user_image_head;
    private String user_sex;
    private String user_brithday;
    private String user_address;
    private String job;
    private String phone;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_image_head() {
        return user_image_head;
    }

    public void setUser_image_head(String user_image_head) {
        this.user_image_head = user_image_head;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_brithday() {
        return user_brithday;
    }

    public void setUser_brithday(String user_brithday) {
        this.user_brithday = user_brithday;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
