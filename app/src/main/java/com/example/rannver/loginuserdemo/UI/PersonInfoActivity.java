package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Adpter.FriendGroupAdpter;
import com.example.rannver.loginuserdemo.Data.FriendGroupBean;
import com.example.rannver.loginuserdemo.Data.PersonFriend;
import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.lin_info_box)
    LinearLayout linInfoBox;
    @BindView(R.id.lin_addfriend)
    LinearLayout linAddfriend;     //先用来做添加好友的点击事件

    private String intent_flag = null;
    private String intent_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        intent_name = intent_login_flag.getStringExtra("login_name");

        Toast.makeText(PersonInfoActivity.this, intent_flag + "，" + intent_name, Toast.LENGTH_SHORT).show();

        //个人信息页面显示
        List<PersonInfomation> info_list = DataSupport.where("username = ?", intent_name).find(PersonInfomation.class);
        String name = "";
        String sex = "";
        String job = "";
        String head_image_path = "";
        int id = 0;
        int care_id = 0;
        int becare_id = 0;
        List<PersonFriend> list_friend = null;
        for (PersonInfomation personInfomation : info_list) {
            name = personInfomation.getUsername();
            sex = personInfomation.getGender();
            job = personInfomation.getCareer();
            head_image_path = personInfomation.getPortraitUrl();
            id = personInfomation.getId();
            care_id = personInfomation.getCare();
            becare_id = personInfomation.getBecare();
            list_friend = personInfomation.getFriendList();
        }
        System.out.println("Info:" + name + " " + sex + " " + job + " " + head_image_path);
        //设置文字信息
        infoUsername.setText(name);
        infoUsersex.setText(sex);
        infoUserjob.setText(job);
        //设置图片信息
        if (head_image_path != null) {
            File file = new File(head_image_path);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(head_image_path);
                ivInfoHead.setImageBitmap(bitmap);
            }
        }


        //好友页面显示
        if (intent_flag != null) {
            if (intent_flag.equals("1") || intent_flag.equals("2")) {
                List<FriendGroupBean> friendlist = LoadFriendData(list_friend,care_id,becare_id);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                listFriend.setLayoutManager(linearLayoutManager);
                FriendGroupAdpter friendGroupAdpter = new FriendGroupAdpter(friendlist, intent_flag);
                friendGroupAdpter.SetOnItemClickListener(new FriendGroupAdpter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //2017.2.11设置RecycleView的监听事件
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });
                listFriend.setAdapter(friendGroupAdpter);

            } else {
                Toast.makeText(PersonInfoActivity.this, "程序跳转异常 x02", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(PersonInfoActivity.this, "程序跳转异常 x01", Toast.LENGTH_SHORT).show();
        }

        //个人信息页功能实现
        //点击进入详细个人信息页事件
        linBtuInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_detai = new Intent(PersonInfoActivity.this, PersonInfoDetaiActivity.class);
                intent_detai.putExtra("login_flag", intent_flag);
                intent_detai.putExtra("login_name", intent_name);
                startActivity(intent_detai);
            }
        });

        //点击进入消息中心事件
        linInfoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //点击进入好友查询事件
        linAddfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_check = new Intent(PersonInfoActivity.this,ChooseFriendActivity.class);
                intent_check.putExtra("login_flag", intent_flag);
                intent_check.putExtra("login_name", intent_name);
                startActivity(intent_check);
            }
        });

    }

    private List<FriendGroupBean> LoadFriendData(List<PersonFriend> list_friend,int care_id,int becare_id) {
        List<FriendGroupBean> list = new ArrayList<FriendGroupBean>();

        //数据库读取好友信息

        if (list_friend!=null){
            for (PersonFriend personfriend:list_friend){
                FriendGroupBean friend = new FriendGroupBean();

                int id = personfriend.getFriend_id();
                String remark = personfriend.getFriend_remark();
                String relation = personfriend.getFriend_relation();
                String head_image_url = "";
                String user_name = "";
                String job = "";
                int age = 0;
                int friend_id = 0;
                String sex = "";
                String flag = "";

                List<PersonInfomation> information = DataSupport.where("id = ?", String.valueOf(id)).find(PersonInfomation.class);
                for (PersonInfomation person:information){
                    head_image_url = person.getPortraitUrl();
                    user_name = person.getUsername();
                    job = person.getCareer();
                    age = person.getAge();
                    sex = person.getGender();
                    friend_id = person.getId();
                }

                friend.setFriend_remark(remark);
                friend.setHead_image_path(head_image_url);
                friend.setFriend_name(user_name);
                friend.setFriend_job(job);
                friend.setFriend_age(String.valueOf(age));
                friend.setFriend_sex(sex);
                if (friend_id==care_id){
                    flag = "2";
                }else if (friend_id==becare_id){
                    flag = "3";
                }else {
                    flag = "1";
                }
                friend.setFriend_flag(flag);
                list.add(friend);
            }
        }


        FriendGroupBean friendGroupBean = new FriendGroupBean();
        friendGroupBean.setFriend_name("#/A");
        friendGroupBean.setFriend_job("#/A");
        friendGroupBean.setFriend_age("#/A");
        friendGroupBean.setFriend_sex("#/A");

        list.add(friendGroupBean);

        return list;
    }
}
