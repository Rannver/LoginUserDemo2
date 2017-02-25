package com.example.rannver.loginuserdemo.Data.gsonBean;

/**
 * Created by Rannver on 2017/2/21.
 */

public class PersonInfoGsonBean {

    /**
     * address : testaddress
     * age : 0
     * beCare : 0
     * birthday : 846950400000
     * care : 0
     * career : testcareer
     * gender : 鐢�
     * id : 61
     * phone_number : 123456789
     * portrait_url : http://youyinnn.cn/FamilyAssistant/Portrait/admin2.jpg
     * username : admin2
     */

    private String address;
    private int age;
    private int beCare;
    private long birthday;
    private int care;
    private String career;
    private String gender;
    private int id;
    private long phone_number;
    private String portrait_url;
    private String username;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBeCare() {
        return beCare;
    }

    public void setBeCare(int beCare) {
        this.beCare = beCare;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getCare() {
        return care;
    }

    public void setCare(int care) {
        this.care = care;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(long phone_number) {
        this.phone_number = phone_number;
    }

    public String getPortrait_url() {
        return portrait_url;
    }

    public void setPortrait_url(String portrait_url) {
        this.portrait_url = portrait_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
