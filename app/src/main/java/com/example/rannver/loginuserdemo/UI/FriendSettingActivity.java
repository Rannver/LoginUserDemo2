package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.WebApi.AddFriendApi;
import com.example.rannver.loginuserdemo.WebApi.ChangeFriendInfoApi;
import com.example.rannver.loginuserdemo.WebService.AddFriendService;
import com.example.rannver.loginuserdemo.WebService.ChangeFriendInfoService;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private String intent_friend_id = null;
    private String intent_friend_flag = null;
    private String relation= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_setting_activity);
        ButterKnife.bind(this);

        final Intent intent = getIntent();
        intent_flag = intent.getStringExtra("login_flag");
        intent_name = intent.getStringExtra("login_name");
        intent_friend_id = intent.getStringExtra("friend_id");
        intent_friend_flag = intent.getStringExtra("friend_flag");
        System.out.println("FriendSettingActivity:"+intent_flag+","+intent_name+","+intent_friend_id+","+intent_friend_flag);

        //设置返回事件
        ivSetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent_friend_flag.equals("add")){
                    Intent intent_back = new Intent(FriendSettingActivity.this,ChooseFriendActivity.class);
                    intent_back.putExtra("login_flag",intent_flag);
                    intent_back.putExtra("login_name",intent_name);
                    startActivity(intent_back);
                }else {
                    Intent intent_back = new Intent(FriendSettingActivity.this,PersonInfoDetaiActivity.class);
                    intent_back.putExtra("login_flag",intent_flag);
                    intent_back.putExtra("login_name",intent_name);
                    startActivity(intent_back);
                }
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

        //确定
        btuSetComfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //备注信息
                String remark = edSetRemark.getText().toString();

                System.out.print("group:"+groupRelation.getCheckedRadioButtonId());
                if (groupRelation.getCheckedRadioButtonId()==-1){
                    Toast.makeText(FriendSettingActivity.this,"请选择好友关系",Toast.LENGTH_SHORT).show();
                }else {
                    //存入好友表
                    SaveCloudFriend(intent_name,intent_friend_id,remark,relation,intent_friend_flag);
                    //跳转至个人信息界面
                    Intent intent_info = new Intent(FriendSettingActivity.this,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent_info.putExtra("login_flag",intent_flag);
                    intent_info.putExtra("login_name",intent_name);
                    startActivity(intent_info);
                }
            }
        });
    }

    //设置的好友信息更新云端数据库
    private void SaveCloudFriend(final String intent_name, final String intent_friend_id, final String remark, final String relation, final String intent_friend_flag) {

        /*intent_name:用户名
        intent_friend_id:好友id
        remark:好友备注
        relation:好友关系
        intnet_friend_flag:好友备注关系操作标识符
        */
        System.out.println("friend_setting1:"+intent_name+","+intent_friend_id+","+remark+","+relation+","+intent_friend_flag);
        List<PersonInfomation> infos = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
        int id = 0;
        for (PersonInfomation person:infos){
            id = person.getUser_id();
        }
        switch (intent_friend_flag){
            case "add":
                AddFriendApi addFriendApi = new AddFriendApi();
                AddFriendService addFriendService = addFriendApi.getService();
                Call<String> call_add = addFriendService.getState(id, Integer.parseInt(intent_friend_id));
                call_add.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String flag = response.body();
                        if (flag.equals("true")){
                            List<PersonInfomation> infos = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
                            int id = 0;
                            for (PersonInfomation person:infos){
                                id = person.getUser_id();
                            }
                            SetFriendSetting(id, Integer.parseInt(intent_friend_id),relation,remark);
                            SaveLocalFriend(intent_name,intent_friend_id,remark,relation,intent_friend_flag);
                        }else {
                            Toast.makeText(FriendSettingActivity.this,"好友添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(FriendSettingActivity.this,"好友添加失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case "change":
                ChangeFriendInfoApi changeFriendInfoApi = new ChangeFriendInfoApi();
                ChangeFriendInfoService changeFriendInfoService = changeFriendInfoApi.getService();
                Call<String> call_change = changeFriendInfoService.getState(id, Integer.parseInt(intent_friend_id),relation,remark);
                call_change.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body().equals("true")){
                            SaveLocalFriend(intent_name,intent_friend_id,remark,relation,intent_friend_flag);
                        }else {
                            Toast.makeText(FriendSettingActivity.this,"好友信息更改失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("change_onFailure","更改好友信息设置失败,"+t);
                    }
                });
                break;
            default:
                break;
        }

    }
    private void SetFriendSetting(int id,int friend_id,String relation,String remark){
        ChangeFriendInfoApi changeFriendInfoApi = new ChangeFriendInfoApi();
        ChangeFriendInfoService changeFriendInfoService = changeFriendInfoApi.getService();
        Call<String> call_change = changeFriendInfoService.getState(id, Integer.parseInt(intent_friend_id),relation,remark);
        call_change.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("false")){
                    Toast.makeText(FriendSettingActivity.this,"好友信息更改失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(FriendSettingActivity.this,"好友信息更改失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //设置的好友信息更新本地数据库
    private void SaveLocalFriend(String intent_name, String intent_friend_id, String remark, String relation,String intent_friend_flag) {

        /*intent_name:用户名
        intent_friend_id:好友id
        remark:好友备注
        relation:好友关系
        intnet_friend_flag:好友备注关系操作标识符
        */

        System.out.println("friend_setting2:"+intent_name+","+intent_friend_id+","+remark+","+relation+","+intent_friend_flag);
        switch (intent_friend_flag){
            case "add":
                //添加
                List<PersonInfomation> personInfomations = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
                int user_id = 0;
                for (PersonInfomation personInfo:personInfomations){
                    user_id = personInfo.getId();
                }
                PersonFriend personFriend = new PersonFriend();
                personFriend.setFriend_id(Integer.parseInt(intent_friend_id));
                personFriend.setFriend_remark(remark);
                personFriend.setFriend_relationship(relation);
                personFriend.setPersoninfomation_id(user_id);
                personFriend.save();
                break;
            case "change":
                //更改
                List<PersonInfomation> personInfomations_change = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
                int chage_user_id = 0;
                for (PersonInfomation personInfomation:personInfomations_change){
                    chage_user_id = personInfomation.getId();
                }
                PersonFriend personFriend_chage = new PersonFriend();
                personFriend_chage.setFriend_remark(remark);
                personFriend_chage.setFriend_relationship(relation);
                personFriend_chage.setPersoninfomation_id(chage_user_id);
                personFriend_chage.updateAll("personinfomation_id = ? and friend_id = ?", String.valueOf(chage_user_id),intent_friend_id);
                break;
            default:
                break;
        }

    }
}
