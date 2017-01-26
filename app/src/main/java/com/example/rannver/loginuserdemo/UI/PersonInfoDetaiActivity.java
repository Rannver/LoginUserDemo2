package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/26.
 */

public class PersonInfoDetaiActivity extends AppCompatActivity {

    @BindView(R.id.btu_info_detai_edit)
    Button btuInfoDetaiEdit;
    @BindView(R.id.iv_detai_head)
    CircleImageView ivDetaiHead;
    @BindView(R.id.tv_info_detai_id)
    TextView tvInfoDetaiId;
    @BindView(R.id.tv_info_detai_name)
    TextView tvInfoDetaiName;
    @BindView(R.id.tv_info_detai_sex)
    TextView tvInfoDetaiSex;
    @BindView(R.id.tv_info_detai_year)
    TextView tvInfoDetaiYear;
    @BindView(R.id.tv_info_detai_month)
    TextView tvInfoDetaiMonth;
    @BindView(R.id.tv_info_detai_day)
    TextView tvInfoDetaiDay;
    @BindView(R.id.tv_info_detai_address)
    TextView tvInfoDetaiAddress;
    @BindView(R.id.tv_info_detai_job)
    TextView tvInfoDetaiJob;
    @BindView(R.id.tv_info_detai_phone)
    TextView tvInfoDetaiPhone;
    @BindView(R.id.iv_detai_back)
    ImageView ivDetaiBack;
    @BindView(R.id.btu_sigup_back)
    Button btuSigupBack;

    private String intent_flag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_detai_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        Toast.makeText(PersonInfoDetaiActivity.this,intent_flag,Toast.LENGTH_SHORT).show();

        //点击返回
        ivDetaiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(PersonInfoDetaiActivity.this, PersonInfoActivity.class);
                intent_back.putExtra("login_flag",intent_flag);
                startActivity(intent_back);
            }
        });
        //点击编辑个人信息
        btuInfoDetaiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_edit = new Intent(PersonInfoDetaiActivity.this,PersonInfoEditActivity.class);
                intent_edit.putExtra("login_flag",intent_flag);
                startActivity(intent_edit);
            }
        });
        //点击退出登录事件
        btuSigupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_signup_back = new Intent(PersonInfoDetaiActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_signup_back);
            }
        });


    }
}
