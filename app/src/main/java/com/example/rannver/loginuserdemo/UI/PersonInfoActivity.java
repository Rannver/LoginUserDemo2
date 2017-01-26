package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/26.
 */

public class PersonInfoActivity extends AppCompatActivity {

    @BindView(R.id.iv_info_head)
    CircleImageView ivInfoHead;
    @BindView(R.id.info_username)
    TextView infoUsername;
    @BindView(R.id.info_usersex)
    TextView infoUsersex;
    @BindView(R.id.info_userjob)
    TextView infoUserjob;
    @BindView(R.id.lin_btu_info)
    LinearLayout linBtuInfo;
    @BindView(R.id.list_friend)
    RecyclerView listFriend;

    private String intent_flag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");

        Toast.makeText(PersonInfoActivity.this,intent_flag,Toast.LENGTH_SHORT).show();

        //页面显示
        if (intent_flag!=null){
            if (intent_flag.equals("1")){
                //普通登录页面显示

            }else if (intent_flag.equals("2")){
                //老年登录页面显示

            }else {
                Toast.makeText(PersonInfoActivity.this,"程序跳转异常 x02",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(PersonInfoActivity.this,"程序跳转异常 x01",Toast.LENGTH_SHORT).show();
        }

        //个人信息页功能实现
        //点击进入详细个人信息页事件
        linBtuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_detai = new Intent(PersonInfoActivity.this,PersonInfoDetaiActivity.class);
                intent_detai.putExtra("login_flag",intent_flag);
                startActivity(intent_detai);
            }
        });



    }
}
