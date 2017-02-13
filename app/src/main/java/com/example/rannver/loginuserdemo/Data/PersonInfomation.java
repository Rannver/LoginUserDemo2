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

    private int id;  //用户id
    private int age; //用户年龄
    private int care; //特别关心谁
    private int becare;  //被谁特别关心
    private String gender; //性别
    private long phoneNumber; //电话号码
    private String name;  //姓名
    private String career;//职业
    private String address; //住址
    private String username;//用户名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public int getBecare() {
        return becare;
    }

    public void setBecare(int becare) {
        this.becare = becare;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<PersonFriend> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<PersonFriend> friendList) {
        this.friendList = friendList;
    }

    private String password;//用户密码
    private String portraitUrl;//头像Url
    private Date birthday;//生日
    private List<PersonFriend> friendList;//好友列表

}
