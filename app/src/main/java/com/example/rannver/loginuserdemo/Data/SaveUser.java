package com.example.rannver.loginuserdemo.Data;

import org.litepal.crud.DataSupport;

/**
 * Created by Rannver on 2017/2/16.
 */

public class SaveUser extends DataSupport {

    private String username;
    private String password;
    private int    flag;  //普通显示还时老年显示
    private int    staus; //当前用户状态，1为当前登录状态，0为普通状态

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }
}
