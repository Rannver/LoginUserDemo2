package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Adpter.FriendGroupAdpter;
import com.example.rannver.loginuserdemo.Data.dbTable.SaveUser;
import com.example.rannver.loginuserdemo.Data.gsonBean.FriendGsonBean;
import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;
import com.example.rannver.loginuserdemo.Data.listBean.FriendGroupBean;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.PopuWindowConfirm;
import com.example.rannver.loginuserdemo.WebApi.GetFriendInfoApi;
import com.example.rannver.loginuserdemo.WebApi.LoginApi;
import com.example.rannver.loginuserdemo.WebApi.ShowFriendInfoApi;
import com.example.rannver.loginuserdemo.WebService.GetFriendInfoService;
import com.example.rannver.loginuserdemo.WebService.LoginService;
import com.example.rannver.loginuserdemo.WebService.ShowFriendInfoService;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        UpdateData();

        //获取个人信息并显示
        final List<PersonInfomation> info_list = DataSupport.where("username = ?", intent_name).find(PersonInfomation.class);
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
            head_image_path = personInfomation.getPortrait_url();
            id = personInfomation.getUser_id();
            care_id = personInfomation.getCare();
            becare_id = personInfomation.getBecare();
            list_friend = personInfomation.getFriend_list();
            Date birthday = personInfomation.getBirthday();
            System.out.println("info_birthday:"+birthday);
        }
        System.out.println("Info:" + name + " " + sex + " " + job + " " + head_image_path+" "+list_friend.size());
        //设置文字信息
        infoUsername.setText(name);
        infoUsersex.setText(sex);
        infoUserjob.setText(job);
        //设置图片信息
        if (head_image_path != null) {
            Picasso.with(PersonInfoActivity.this).load(head_image_path).into(ivInfoHead);
        }


        //好友页面显示
        if (intent_flag != null) {
            if (intent_flag.equals("1") || intent_flag.equals("2")) {
                final List<FriendGroupBean> friendlist = LoadFriendData(list_friend,care_id,becare_id);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                listFriend.setLayoutManager(linearLayoutManager);
                FriendGroupAdpter friendGroupAdpter = new FriendGroupAdpter(friendlist, intent_flag,PersonInfoActivity.this);
                friendGroupAdpter.SetOnItemClickListener(new FriendGroupAdpter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //短按进入好友详细信息界面
                        Intent intent_detail = new Intent(PersonInfoActivity.this,FriendInfoDetailActivity.class);
                        intent_detail.putExtra("login_flag",intent_flag);
                        intent_detail.putExtra("login_name",intent_name);
                        intent_detail.putExtra("friend_id",friendlist.get(position).getFriend_id());
                        startActivity(intent_detail);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //长按设置为特别关心
                        PopuWindowConfirm popuWindowConfirm = new PopuWindowConfirm(PersonInfoActivity.this,PersonInfoActivity.this,intent_name,intent_flag);
                        if (friendlist.get(position).getFriend_flag().equals("2")||friendlist.get(position).getFriend_flag().equals("4")){
                            popuWindowConfirm.CancelCareFriend(friendlist.get(position).getFriend_name(),friendlist.get(position).getFriend_remark(),friendlist.get(position).getFriend_id(),intent_name);
                        }else {
                            popuWindowConfirm.SetCareFriend(friendlist.get(position).getFriend_name(),friendlist.get(position).getFriend_remark(),friendlist.get(position).getFriend_id(),intent_name);
                        }
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

    //更新用户信息
    private void UpdateData() {
        List<SaveUser> users = DataSupport.where("username = ?",intent_name).find(SaveUser.class);
        String pwd = "";
        for (SaveUser user :users){
            pwd = user.getPassword();
        }
        LoginApi loginApi = new LoginApi();
        LoginService loginService = loginApi.getService();
        Call<PersonInfoGsonBean> call_login = loginService.getState(intent_name,pwd);
        call_login.enqueue(new Callback<PersonInfoGsonBean>() {
            @Override
            public void onResponse(Call<PersonInfoGsonBean> call, Response<PersonInfoGsonBean> response) {
                SaveLocalUserData(response);
            }

            @Override
            public void onFailure(Call<PersonInfoGsonBean> call, Throwable t) {
                Toast.makeText(PersonInfoActivity.this,"网络连接错误，请重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //将用户信息存入本地数据库
    private void SaveLocalUserData(Response<PersonInfoGsonBean> response) {
        PersonInfoGsonBean personInfo = response.body();
        final String username = personInfo.getUsername();
        final String path = personInfo.getPortrait_url();
        long phone = personInfo.getPhone_number();
        final int  id = personInfo.getId();
        String sex = personInfo.getGender();
        String job = personInfo.getCareer();
        int care = personInfo.getCare();
        int becare = personInfo.getBeCare();
        int age = personInfo.getAge();
        String address = personInfo.getAddress();
        Date birthday = new Date(personInfo.getBirthday());
        System.out.println("SaveLocalUserData:"+username+","+path+","+phone+","+id+","+sex+","+job+","+care+","+becare+","+age+","+address);
        //存入本地数据库
        //本地存储用户个人信息
        final List<PersonInfomation> list = DataSupport.where("username = ?",username).find(PersonInfomation.class);
        PersonInfomation personInfomation = new PersonInfomation();
        personInfomation.setUsername(username);
        personInfomation.setPortrait_url(path);
        personInfomation.setPhone_number(phone);
        personInfomation.setUser_id(id);
        personInfomation.setGender(sex);
        personInfomation.setCareer(job);
        personInfomation.setCare(care);
        personInfomation.setBecare(becare);
        personInfomation.setAge(age);
        personInfomation.setAddress(address);
        personInfomation.setBirthday(birthday);
        if (list.size()==0){
            personInfomation.save();
        }else {
            personInfomation.updateAll("username = ?",username);
        }
        //本地存储用户好友信息
        GetFriendInfoApi getFriendInfoApi = new GetFriendInfoApi();
        GetFriendInfoService getFriendInfoService = getFriendInfoApi.getService();
        Call<List<FriendGsonBean>> call_friendList = getFriendInfoService.getFriendList(id);
        call_friendList.enqueue(new Callback<List<FriendGsonBean>>() {
            @Override
            public void onResponse(Call<List<FriendGsonBean>> call, Response<List<FriendGsonBean>> response) {
                List<FriendGsonBean> friendList = response.body();
                System.out.println("friendList.size2():"+friendList.size());
                if (friendList.size()!=0){
                    //获取用户用于获取好友信息的id
                    List<PersonInfomation> infos = DataSupport.where("username = ?",username).find(PersonInfomation.class);
                    int key_id = 0;
                    for (PersonInfomation person:infos){
                        key_id = person.getId();
                    }
                    //将好友信息存入本地数据库
                    for (FriendGsonBean friendGsonBean:friendList){
                        int friend_id = friendGsonBean.getFriend_id();
                        String remark = friendGsonBean.getRemark();
                        String relation = friendGsonBean.getRelationship();
                        List<PersonFriend> friends = DataSupport.where("friend_id = ? and personinfomation_id = ?",String.valueOf(friend_id),String.valueOf(key_id)).find(PersonFriend.class);
                        PersonFriend personFriend = new PersonFriend();
                        personFriend.setFriend_id(friend_id);
                        personFriend.setPersoninfomation_id(key_id);
                        personFriend.setFriend_remark(remark);
                        personFriend.setFriend_relationship(relation);
                        if (friends.size()==0){
                            boolean flag = personFriend.save();
                            System.out.println("personFriend.save():"+flag);
                        }else {
                            int flag = personFriend.updateAll("friend_id = ? and personinfomation_id = ?",String.valueOf(friend_id),String.valueOf(key_id));
                            System.out.println("personFriend.updateAll():"+flag);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FriendGsonBean>> call, Throwable t) {
                Log.d("onFailure","请求失败：call_friendList");
            }
        });
    }

    private List<FriendGroupBean> LoadFriendData(List<PersonFriend> list_friend, final int care_id, final int becare_id) {

        final List<FriendGroupBean> list = new ArrayList<FriendGroupBean>();
        //后台数据库读取好友信息
        if (list_friend!=null){
            for (PersonFriend personfriend:list_friend){

                final FriendGroupBean friend = new FriendGroupBean();
                final int id = personfriend.getFriend_id();
                final String remark = personfriend.getFriend_remark();
                String relation = personfriend.getFriend_relationship();

                ShowFriendInfoApi showFriendInfoApi = new ShowFriendInfoApi();
                ShowFriendInfoService showFriendInfoService = showFriendInfoApi.getService();
                Call<PersonInfoGsonBean> call_friendInfo = showFriendInfoService.getFriendInfo(id);
                call_friendInfo.enqueue(new Callback<PersonInfoGsonBean>() {
                    @Override
                    public void onResponse(Call<PersonInfoGsonBean> call, Response<PersonInfoGsonBean> response) {
                        if (response.body()!=null){
                            String head_image_url = response.body().getPortrait_url();
                            String user_name = response.body().getUsername();
                            String job = response.body().getCareer();
                            int age = response.body().getAge();
                            int friend_id = response.body().getId();
                            String sex = response.body().getGender();
                            String flag = "";
                            friend.setFriend_id(String.valueOf(id));
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
                            }else if (care_id==becare_id&&care_id!=0&&becare_id!=0){
                                flag = "4";
                            }else {
                                flag = "1";
                            }
                            friend.setFriend_flag(flag);
                            list.add(friend);
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonInfoGsonBean> call, Throwable t) {
                        Log.d("friendInfo_onFailure","获取好友"+id+"的基本信息失败");
                    }
                });
            }
        }

        return list;
    }
}
