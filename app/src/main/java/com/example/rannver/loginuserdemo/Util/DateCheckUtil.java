package com.example.rannver.loginuserdemo.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rannver on 2017/1/25.
 */

public class DateCheckUtil {

    //日期检测
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


    //Date转换成Str
    private String BirthdayDateToStr(Date date){
        String string = "";

        if (date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            string = sdf.format(date);
            System.out.println("detail_birthday:"+string);
        }
        return string;
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

    //将日期转换成毫秒数
    public long DateToMillionSeconds(Date date){
        long ms = date.getTime();
        return ms;
    }
    //年龄计算
    public int CalAge(int year, int month, int day){
        int year_now;
        int month_now;
        int day_now;
        SimpleDateFormat sdf_now_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf_now_month = new SimpleDateFormat("MM");
        SimpleDateFormat sdf_now_day = new SimpleDateFormat("dd");
        year_now = Integer.parseInt(sdf_now_year.format(new Date()));
        month_now = Integer.parseInt(sdf_now_month.format(new Date()));
        day_now = Integer.parseInt(sdf_now_day.format(new Date()));

        System.out.println("now_time:"+year_now+"-"+month_now+"-"+day_now);

        int age = year_now-year;
        if (month_now<=month){
            if (month_now==month){
                if (day_now<day){
                    age--;
                }
            }else {
                age--;
            }
        }


        return age;
    }
}
