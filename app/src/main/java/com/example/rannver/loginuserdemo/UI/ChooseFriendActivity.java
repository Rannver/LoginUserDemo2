package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rannver.loginuserdemo.Adpter.ChooseListAdpter;
import com.example.rannver.loginuserdemo.Data.ChooseGroupBean;
import com.example.rannver.loginuserdemo.Data.FriendGroupBean;
import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

        //确定键点击事件
        btuChooseComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkstr = edChooseInput.getText().toString();
                List<PersonInfomation> list_id = DataSupport. where("user_id = ?",checkstr).find(PersonInfomation.class);
                List<PersonInfomation> list_name = DataSupport.where("user_name = ?",checkstr).find(PersonInfomation.class);
                List<ChooseGroupBean> list_search = new ArrayList<ChooseGroupBean>();
                Log.d("size",list_id.size()+","+list_name.size());
                if (list_id.size()>0){
                    for (PersonInfomation personInfomation:list_id){
                       ChooseGroupBean GroupBean = new ChooseGroupBean();
                        GroupBean.setFriend_id(String.valueOf(personInfomation.getUser_id()));
                        GroupBean.setFriend_name(personInfomation.getUser_name());
                        GroupBean.setFriend_sex(personInfomation.getUser_sex());
                        GroupBean.setFriend_age(personInfomation.getUser_brithday());//之后记得换成年龄计算
                        System.out.println("information:"+personInfomation.getUser_id()+","+personInfomation.getUser_name()+","+personInfomation.getUser_sex()+","+personInfomation.getUser_brithday());
                        list_search.add(GroupBean);
                    }
                }
                if (list_name.size()>0){
                    for (PersonInfomation personInfomation:list_name){
                        ChooseGroupBean GroupBean = new ChooseGroupBean();
                        GroupBean.setFriend_id(String.valueOf(personInfomation.getUser_id()));
                        GroupBean.setFriend_name(personInfomation.getUser_name());
                        GroupBean.setFriend_sex(personInfomation.getUser_sex());
                        GroupBean.setFriend_age(personInfomation.getUser_brithday());//之后记得换成年龄计算
                        System.out.println("information:"+personInfomation.getUser_id()+","+personInfomation.getUser_name()+","+personInfomation.getUser_sex()+","+personInfomation.getUser_brithday());
                        list_search.add(GroupBean);
                    }
                }
                Log.d("size", String.valueOf(list_search.size()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseFriendActivity.this);
                chooseList.setLayoutManager(linearLayoutManager);
                ChooseListAdpter chooseListAdpter = new ChooseListAdpter(list_search,ChooseFriendActivity.this,intent_flag,intent_name);
                chooseList.setAdapter(chooseListAdpter);

            }
        });

    }
}
