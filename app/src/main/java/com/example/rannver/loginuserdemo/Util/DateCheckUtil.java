package com.example.rannver.loginuserdemo.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rannver on 2017/1/25.
 */

public class DateCheckUtil {

    public boolean CheckDateFromDay(String year,String month, String day){
        int y = Integer.parseInt(year);
        int m = Integer.parseInt(month);
        int d = Integer.parseInt(day);
        int year_now;   //当前系统年份

        //当前系统年份
        SimpleDateFormat sdf_now_year = new SimpleDateFormat("yyyy");
        year_now = Integer.parseInt(sdf_now_year.format(new Date()));
        if (year.length()!=4||month.length()>2||day.length()>2){
            return false;
        } else if (y>year_now){
            return false;
        }else if (m>12||m<=0){
            return false;
        }else if (d>ReturnDay(y,m,d)||d<0){
            return false;
        }else {
            return true;
        }
    }

    private int ReturnDay(int y, int m, int d) {

        if (y%4!=0){
            if (m==1||m==3||m==5||m==7||m==8||m==10||m==12){
                return 31;
            }else if (m==2){
                return 28;
            }else{
                return 30;
            }

        }else {
            if (m==1||m==3||m==5||m==7||m==8||m==10||m==12){
                return 31;
            }else if (m==2){
                return 29;
            }else{
                return 30;
            }
        }
    }

}
