package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rannver.loginuserdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/2/9.
 */

public class ChooseFriendActivity extends AppCompatActivity {
    @BindView(R.id.iv_choose_back)
    ImageView ivChooseBack;
    @BindView(R.id.btu_choose_comfirm)
    Button btuChooseComfirm;
    @BindView(R.id.ed_choose_input)
    EditText edChooseInput;
    @BindView(R.id.choose_list)
    RecyclerView chooseList;

    private String intent_flag = null;
    private String intent_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosefriend_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        intent_name = intent_login_flag.getStringExtra("login_name");

        //点击返回事件
        ivChooseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(ChooseFriendActivity.this,PersonInfoActivity.class);
                intent_back.putExtra("login_flag", intent_flag);
                intent_back.putExtra("login_name", intent_name);
                startActivity(intent_back);
            }
        });

    }
}
