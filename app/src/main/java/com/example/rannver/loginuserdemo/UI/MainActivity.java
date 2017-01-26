package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rannver.loginuserdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btu_login_1)
    Button btuLogin1;
    @BindView(R.id.btu_login_2)
    Button btuLogin2;
    @BindView(R.id.tv_sinup_user)
    TextView tvSinupUser;
    @BindView(R.id.tv_change_pwd)
    TextView tvChangePwd;
    @BindView(R.id.ed_login_id)
    EditText edLoginId;
    @BindView(R.id.ed_login_pwd)
    EditText edLoginPwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String User_login_id = edLoginId.getText().toString();
        String User_login_pwd = edLoginPwd.getText().toString();

        //普通登录入口
        btuLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转
                Intent intent_1 = new Intent(MainActivity.this,PersonInfoActivity.class);
                intent_1.putExtra("login_flag","1");
                startActivity(intent_1);
            }
        });

        //老年登录入口
        btuLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转
                Intent intent_2 = new Intent(MainActivity.this,PersonInfoActivity.class);
                intent_2.putExtra("login_flag","2");
                startActivity(intent_2);
            }
        });

        //忘记密码入口
        tvChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //注册入口
        tvSinupUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }
}
