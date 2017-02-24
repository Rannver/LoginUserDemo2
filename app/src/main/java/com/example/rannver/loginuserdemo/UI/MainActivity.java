package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.Data.dbTable.SaveUser;
import com.example.rannver.loginuserdemo.Data.gsonBean.FriendGsonBean;
import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.WebApi.GetFriendInfoApi;
import com.example.rannver.loginuserdemo.WebApi.LoginApi;
import com.example.rannver.loginuserdemo.WebApi.VerifyApi;
import com.example.rannver.loginuserdemo.WebService.GetFriendInfoService;
import com.example.rannver.loginuserdemo.WebService.LoginService;
import com.example.rannver.loginuserdemo.WebService.VerifyService;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //登录时的数据库操作
        CheckLogin();

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

                //登录跳转
                if(User_login_id.equals("")){
                    Toast.makeText(MainActivity.this, "用户名未填写", Toast.LENGTH_SHORT).show();
                }else if(User_login_pwd.equals("")){
                    Toast.makeText(MainActivity.this, "用户密码未填写", Toast.LENGTH_SHORT).show();
                }else {
                    CheckVerify(User_login_id,User_login_pwd,"1");
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
                //登录跳转
                if(User_login_id.equals("")){
                    Toast.makeText(MainActivity.this, "用户名未填写", Toast.LENGTH_SHORT).show();
                }else if(User_login_pwd.equals("")){
                    Toast.makeText(MainActivity.this, "用户密码未填写", Toast.LENGTH_SHORT).show();
                }else {
                    CheckVerify(User_login_id,User_login_pwd,"2");
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

    //登录判断（从后台）
    private void GetUserState(final String name, final String pwd, final String flag) {
        LoginApi loginApi = new LoginApi();
        LoginService loginService = loginApi.getService();
        Call<PersonInfoGsonBean> call_login = loginService.getState(name,pwd);
        call_login.enqueue(new Callback<PersonInfoGsonBean>() {
            @Override
            public void onResponse(Call<PersonInfoGsonBean> call, Response<PersonInfoGsonBean> response) {
                if (response.body()==null){
                    //登录失败
                    Toast.makeText(MainActivity.this,"用户名或密码错误，请重试",Toast.LENGTH_SHORT).show();
                }else {
                    //登录成功
                    //更新SaveUser表
                    SaveUserTable(name,pwd,2);
                    //解析用户信息存入本地数据库
                    SaveLocalUserData(response);
                    //跳转至用户个人信息界面
                    Intent intent_1 = new Intent(MainActivity.this,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_1.putExtra("login_flag",flag);
                    intent_1.putExtra("login_name",name);
                    startActivity(intent_1);

                }
            }

            @Override
            public void onFailure(Call<PersonInfoGsonBean> call, Throwable t) {
                Toast.makeText(MainActivity.this,"连接失败 x2"+" "+t,Toast.LENGTH_SHORT).show();
                System.out.print("throwable:"+t);
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



    //用户验证
    private void CheckVerify(final String name, final String pwd, final String flag) {
        VerifyApi verifyApi = new VerifyApi();
        VerifyService verifyService = verifyApi.getService();
        Call<String> call_verify = verifyService.getState(name);
        call_verify.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String str = response.body();
                System.out.println("CheckVerify():"+str);
                if (str.equals("false")){
                    Toast.makeText(MainActivity.this,"该用户尚未注册",Toast.LENGTH_SHORT).show();
                }else {
                    //用户已验证
                    GetUserState(name,pwd,flag);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this,"连接失败 x2",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //打开App登录和退出登录时的数据库操作
    private void CheckLogin() {
        String intent_signup = "";
        Intent intent_get = getIntent();
        intent_signup = intent_get.getStringExtra("login_back");//获取退出登录的用户名
        if (intent_signup==null){
            //点击进入登录事件
            List<SaveUser> users = DataSupport.findAll(SaveUser.class);
            for (SaveUser user:users){
                if (user.getStaus()==1){
                    System.out.println("login:"+user.getFlag()+","+user.getUsername());
                    Intent intent_login = new Intent(MainActivity.this,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    String flag = String.valueOf(user.getFlag());
                    String name = user.getUsername();
                    intent_login.putExtra("login_flag",flag);
                    intent_login.putExtra("login_name",name);
                    startActivity(intent_login);
                    break;
                }
            }
        }else {
            //退出登录切入的登录界面
            edLoginId.setText(intent_signup);
        }
    }

    //更新SaveUser表的信息
    private void SaveUserTable(String name,String pwd,int flag){

        List<SaveUser> users = DataSupport.where("username = ?",name).find(SaveUser.class);
        System.out.println("size():"+users.size());
        if (users.size()==0){
            //之前表中没有存入该用户
            SaveUser user = new SaveUser();
            user.setUsername(name);
            user.setPassword(pwd);
            user.setFlag(flag);
            user.setStaus(1);
            user.save();
        }else {
            //表中已有该用户，更新状态
            SaveUser user = new SaveUser();
            user.setStaus(1);
            user.updateAll("username = ?",name);
        }

    }
}
