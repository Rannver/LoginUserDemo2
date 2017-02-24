package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.PopuWindowConfirm;
import com.example.rannver.loginuserdemo.WebApi.ShowFriendInfoApi;
import com.example.rannver.loginuserdemo.WebService.ShowFriendInfoService;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rannver on 2017/2/13.
 */

public class FriendInfoDetailActivity extends AppCompatActivity {
    @BindView(R.id.iv_detai_back)
    ImageView ivDetaiBack;
    @BindView(R.id.iv_detai_head)
    CircleImageView ivDetaiHead;
    @BindView(R.id.tv_info_firend_id)
    TextView tvInfoFirendId;
    @BindView(R.id.tv_info_firend_name)
    TextView tvInfoFirendName;
    @BindView(R.id.tv_info_firend_sex)
    TextView tvInfoFirendSex;
    @BindView(R.id.tv_info_firend_year)
    TextView tvInfoFirendYear;
    @BindView(R.id.tv_info_firend_address)
    TextView tvInfoFirendAddress;
    @BindView(R.id.tv_info_firend_job)
    TextView tvInfoFirendJob;
    @BindView(R.id.tv_info_firend_phone)
    TextView tvInfoFirendPhone;
    @BindView(R.id.tv_info_friend_relation)
    TextView tvInfoFriendRelation;
    @BindView(R.id.tv_info_friend_remark)
    TextView tvInfoFriendRemark;
    @BindView(R.id.btu_friend_delete)
    Button btuFriendDelete;
    @BindView(R.id.Relative_friend_8)
    RelativeLayout RelativeFriend8;
    @BindView(R.id.Relative_friend_9)
    RelativeLayout RelativeFriend9;
    @BindView(R.id.btu_friend_chat)
    Button btuFriendChat;               //跳转至用户交流功能的Button

    private String intent_flag = null;
    private String intent_name = null;
    private String intent_friend_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_info_detail_activity);
        ButterKnife.bind(this);

        final Intent intent_get = getIntent();
        intent_flag = intent_get.getStringExtra("login_flag");
        intent_name = intent_get.getStringExtra("login_name");
        intent_friend_id = intent_get.getStringExtra("friend_id");
        Toast.makeText(FriendInfoDetailActivity.this, intent_flag + "," + intent_name + "," + intent_friend_id, Toast.LENGTH_SHORT).show();

        //好友详细信息显示
        ShowFriendInfo();

        //点击返回事件
        ivDetaiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(FriendInfoDetailActivity.this, PersonInfoActivity.class);
                intent_back.putExtra("login_flag", intent_flag);
                intent_back.putExtra("login_name", intent_name);
                startActivity(intent_back);
            }
        });
        //点击进入关系和备注设置界面
        RelativeFriend8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_set = new Intent(FriendInfoDetailActivity.this, FriendSettingActivity.class);
                intent_set.putExtra("login_flag", intent_flag);
                intent_set.putExtra("login_name", intent_name);
                intent_set.putExtra("friend_id", intent_friend_id);
                intent_set.putExtra("friend_flag", "change");
                startActivity(intent_set);
            }
        });
        RelativeFriend9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_set = new Intent(FriendInfoDetailActivity.this, FriendSettingActivity.class);
                intent_set.putExtra("login_flag", intent_flag);
                intent_set.putExtra("login_name", intent_name);
                intent_set.putExtra("friend_id", intent_friend_id);
                intent_set.putExtra("friend_flag", "change");
                startActivity(intent_set);
            }
        });
        //点击删除好友事件
        btuFriendDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopuWindowConfirm pop = new PopuWindowConfirm(FriendInfoDetailActivity.this, FriendInfoDetailActivity.this, intent_name, intent_flag);
                pop.DeleteFriendPopu(tvInfoFirendName.getText().toString(), tvInfoFriendRemark.getText().toString(), intent_friend_id, intent_name);
            }
        });
        //点击跳转至用户交流功能事件
        btuFriendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用户交流功能
            }
        });
    }

    private void ShowFriendInfo() {

        //显示好友用户的基本信息
        ShowFriendInfoApi showFriendInfoApi = new ShowFriendInfoApi();
        ShowFriendInfoService showFriendInfoService = showFriendInfoApi.getService();
        Call<PersonInfoGsonBean> call_FriendInfo = showFriendInfoService.getFriendInfo(Integer.parseInt(intent_friend_id));
        call_FriendInfo.enqueue(new Callback<PersonInfoGsonBean>() {
            @Override
            public void onResponse(Call<PersonInfoGsonBean> call, Response<PersonInfoGsonBean> response) {
                if (response.body()!=null){
                     String path = response.body().getPortrait_url();
                    String username = response.body().getUsername();
                    String sex = response.body().getGender();
                    String birthday = "";
                    String address = response.body().getAddress();
                    String job = response.body().getCareer();
                    String phone = String.valueOf(response.body().getPhone_number());
                    tvInfoFirendName.setText(username);
                    tvInfoFirendSex.setText(sex);
                    tvInfoFirendYear.setText(birthday);
                    tvInfoFirendAddress.setText(address);
                    tvInfoFirendJob.setText(job);
                    tvInfoFirendPhone.setText(phone);
                    if (path != null) {
                        Picasso.with(FriendInfoDetailActivity.this).load(path).into(ivDetaiHead);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonInfoGsonBean> call, Throwable t) {
                Log.d("FriendInfo_detail","好友"+intent_friend_id+"的基本信息显示失败");
            }
        });

        //显示用户的好友信息
        List<PersonFriend> friend_list = null;
        String remark = "";
        String relation = "";
        List<PersonInfomation> userinfos = DataSupport.where("username = ?", intent_name).find(PersonInfomation.class);
        for (PersonInfomation user_info : userinfos) {
            friend_list = user_info.getFriend_list();
        }
        for (PersonFriend personfriend : friend_list) {
            String id = String.valueOf(personfriend.getFriend_id());
            if (intent_friend_id.equals(id)) {
                remark = personfriend.getFriend_remark();
                relation = personfriend.getFriend_relationship();
                break;
            }
        }
        tvInfoFirendId.setText(intent_friend_id);
        tvInfoFriendRelation.setText(relation);
        tvInfoFriendRemark.setText(remark);

    }

    private String BirthdayDateToStr(Date date) {
        String string;

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            string = sdf.format(date);
            System.out.println("detail_birthday:" + string);
        } else {
            string = "#/A";
        }
        return string;
    }
}
