package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/2/12.
 * 好友备注和关系设置
 */

public class FriendSettingActivity extends AppCompatActivity {
    @BindView(R.id.group_relation)
    RadioGroup groupRelation;
    @BindView(R.id.ed_set_remark)
    EditText edSetRemark;
    @BindView(R.id.iv_set_back)
    ImageView ivSetBack;
    @BindView(R.id.btu_set_comfirm)
    Button btuSetComfirm;

    private String intent_flag = null;
    private String intent_name = null;
    private String intent_friend = null;
    private String relation= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_setting_activity);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        intent_flag = intent.getStringExtra("login_flag");
        intent_name = intent.getStringExtra("login_name");
        intent_friend = intent.getStringExtra("friend_name");
        System.out.println("FriendSettingActivity:"+intent_flag+","+intent_name+","+intent_friend);

        //设置返回事件
        ivSetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(FriendSettingActivity.this,ChooseFriendActivity.class);
                intent_back.putExtra("login_flag",intent_flag);
                intent_back.putExtra("login_name",intent_name);
                startActivity(intent_back);
            }
        });
        //关系选择功能
        groupRelation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(groupRelation.getCheckedRadioButtonId());
                relation = radioButton.getText().toString();
            }
        });
        //备注
        String remark = edSetRemark.getText().toString();

        //确定
        btuSetComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.print("group:"+groupRelation.getCheckedRadioButtonId());
                if (groupRelation.getCheckedRadioButtonId()==-1){
                    Toast.makeText(FriendSettingActivity.this,"请选择好友关系",Toast.LENGTH_SHORT).show();
                }else {
                    //存入好友表
                    //跳转至个人信息界面
                    Intent intent_info = new Intent(FriendSettingActivity.this,PersonInfoActivity.class);
                    intent_info.putExtra("login_flag",intent_flag);
                    intent_info.putExtra("login_name",intent_name);
                    startActivity(intent_info);
                }
            }
        });
    }
}
