package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.PersonFriend;
import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.PopuWindowTvInfo;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

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

    private String intent_name = "";
    private String intent_pwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //建立数据库
        LitePal.getDatabase();

        //显示来自注册界面跳转的信息
        Intent intent_get = getIntent();
        intent_name = intent_get.getStringExtra("signup_name");
        intent_pwd =  intent_get.getStringExtra("signup_pwd");
        if (intent_name!=null&&intent_pwd!=null){
            edLoginId.setText(intent_name);
            edLoginPwd.setText(intent_pwd);
        }

        //普通登录入口
        btuLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //从UI界面拿到信息
                String User_login_id = edLoginId.getText().toString();
                String User_login_pwd = edLoginPwd.getText().toString();

                //从数据库拿到信息
                List<PersonInfomation> login_db_list = DataSupport.where("username = ?",User_login_id).find(PersonInfomation.class);
                String login_db_name = "";
                String login_db_pwd = "";
                for (PersonInfomation personInfomation:login_db_list){
                    login_db_name = personInfomation.getUsername();
                    login_db_pwd = personInfomation.getPassword();
                }

                Log.d("login","tv_name:"+User_login_id);
                Log.d("login","tv_pwd:"+User_login_pwd);
                Log.d("login","db_name:"+login_db_name);
                Log.d("login","db_pwd:"+login_db_pwd);

                //登录跳转
                if(User_login_id.equals("")){
                    Toast.makeText(MainActivity.this, "用户名未填写", Toast.LENGTH_SHORT).show();
                }else if(User_login_pwd.equals("")){
                    Toast.makeText(MainActivity.this, "用户密码未填写", Toast.LENGTH_SHORT).show();
                }else if ((login_db_name.equals(User_login_id))&&(login_db_pwd.equals(User_login_pwd))){

                    //跳转
                    Intent intent_1 = new Intent(MainActivity.this,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_1.putExtra("login_flag","1");
                    intent_1.putExtra("login_name",login_db_name);
                    startActivity(intent_1);

                }else if (login_db_name.equals("")){
                    Toast.makeText(MainActivity.this, "该用户名未被注册", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "用户名或密码错误，请重试", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //老年登录入口
        btuLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //从UI界面拿到信息
                String User_login_id = edLoginId.getText().toString();
                String User_login_pwd = edLoginPwd.getText().toString();

                //从数据库拿到信息
                List<PersonInfomation> login_db_list = DataSupport.where("username = ?",User_login_id).find(PersonInfomation.class);
                String login_db_name = "";
                String login_db_pwd = "";
                for (PersonInfomation personInfomation:login_db_list){
                    login_db_name = personInfomation.getUsername();
                    login_db_pwd = personInfomation.getPassword();
                }

                Log.d("login","tv_name:"+User_login_id);
                Log.d("login","tv_pwd:"+User_login_pwd);
                Log.d("login","db_name:"+login_db_name);
                Log.d("login","db_pwd:"+login_db_pwd);

                //登录跳转
                if(User_login_id.equals("")){
                    Toast.makeText(MainActivity.this, "用户名未填写", Toast.LENGTH_SHORT).show();
                }else if(User_login_pwd.equals("")){
                    Toast.makeText(MainActivity.this, "用户密码未填写", Toast.LENGTH_SHORT).show();
                }else if ((login_db_name.equals(User_login_id))&&(login_db_pwd.equals(User_login_pwd))){

                    //跳转
                    Intent intent_1 = new Intent(MainActivity.this,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_1.putExtra("login_flag","2");
                    intent_1.putExtra("login_name",login_db_name);
                    startActivity(intent_1);

                }else if (login_db_name.equals("")){
                    Toast.makeText(MainActivity.this, "该用户名未被注册", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "用户名或密码错误，请重试", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //忘记密码入口
        tvChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //忘记密码点击事件
                //还没写
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
