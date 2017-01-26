package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.DateCheckUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowTvInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/26.
 */

public class PersonInfoEditActivity extends AppCompatActivity {
    @BindView(R.id.iv_edit_back)
    ImageView ivEditBack;
    @BindView(R.id.iv_edit_head)
    CircleImageView ivEditHead;
    @BindView(R.id.tv_info_edit_id)
    TextView tvInfoEditId;
    @BindView(R.id.tv_info_edit_name)
    TextView tvInfoEditName;
    @BindView(R.id.btu_edit_sex_boy)
    RadioButton btuEditSexBoy;
    @BindView(R.id.btu_edit_sex_girl)
    RadioButton btuEditSexGirl;
    @BindView(R.id.ed_edit_year)
    EditText edEditYear;
    @BindView(R.id.ed_edit_month)
    EditText edEditMonth;
    @BindView(R.id.ed_edit_day)
    EditText edEditDay;
    @BindView(R.id.ed_edit_address)
    EditText edEditAddress;
    @BindView(R.id.ed_edit_job)
    EditText edEditJob;
    @BindView(R.id.ed_edit_phone)
    EditText edEditPhone;
    @BindView(R.id.btu_edit_save)
    Button btuEditSave;

    private String intent_flag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_edit_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        Toast.makeText(PersonInfoEditActivity.this,intent_flag,Toast.LENGTH_SHORT).show();

        //点击返回
        ivEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(PersonInfoEditActivity.this,PersonInfoDetaiActivity.class);
                intent_back.putExtra("login_flag",intent_flag);
                startActivity(intent_back);
            }
        });

        //显示来自数据库的手机号码等信息

        //性别判断
        ChangeSex();

        //保存
        btuEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = edEditYear.getText().toString();
                String month = edEditMonth.getText().toString();
                String day = edEditDay.getText().toString();
                String address = edEditAddress.getText().toString();
                String job = edEditJob.getText().toString();
                String phone = edEditPhone.getText().toString();
                //检测信息完整度

                if (!CheckBirthday(year,month,day)){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("出生日期填写格式不正确，请检查后保存");
                }else if ((!btuEditSexGirl.isChecked()) && (!btuEditSexBoy.isChecked())){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("请选择你的性别");
                }else {
                    //上传至远程服务器、本地存储用户信息
                    //跳转至详细信息界面
                    Intent intent_detai = new Intent(PersonInfoEditActivity.this,PersonInfoDetaiActivity.class);
                    intent_detai.putExtra("login_flag",intent_flag);
                    startActivity(intent_detai);
                }

            }
        });

    }

    //性别判断
    private void ChangeSex() {

        btuEditSexBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btuEditSexGirl.isChecked()) {
                    btuEditSexBoy.setChecked(true);
                    btuEditSexGirl.setChecked(false);
                }
            }
        });
        btuEditSexGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btuEditSexBoy.isChecked()) {
                    btuEditSexGirl.setChecked(true);
                    btuEditSexBoy.setChecked(false);
                }
            }
        });
    }
    //检测出生日期信息是否正确
    private boolean CheckBirthday(String year,String month,String day) {

        DateCheckUtil dateCheckUtil = new DateCheckUtil();

        //设置出生日期的Date类
        Date date_signup = new Date();
        String date_str = year + "-" + month + "-" + day;
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date_signup = sdf_date.parse(date_str);
            if (!dateCheckUtil.CheckDateFromDay(year, month, day)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
