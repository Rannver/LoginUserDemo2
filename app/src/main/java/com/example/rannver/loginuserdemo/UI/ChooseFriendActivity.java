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
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Adpter.ChooseListAdpter;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.gsonBean.PersonInfoGsonBean;
import com.example.rannver.loginuserdemo.Data.listBean.ChooseGroupBean;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.DateCheckUtil;
import com.example.rannver.loginuserdemo.WebApi.SearchUserApi;
import com.example.rannver.loginuserdemo.WebService.SearchUserService;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                final String checkstr = edChooseInput.getText().toString();
                SearchUserApi searchUserApi = new SearchUserApi();
                SearchUserService searchUserService = searchUserApi.getService();
                Call<PersonInfoGsonBean> call_choose = searchUserService.getList(checkstr);
                call_choose.enqueue(new Callback<PersonInfoGsonBean>() {
                    @Override
                    public void onResponse(Call<PersonInfoGsonBean> call, Response<PersonInfoGsonBean> response) {
                        if (response.body()==null){
                            Toast.makeText(ChooseFriendActivity.this,"未搜索到符合条件的用户",Toast.LENGTH_SHORT).show();
                            List<ChooseGroupBean> list_search = new ArrayList<ChooseGroupBean>();
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseFriendActivity.this);
                            chooseList.setLayoutManager(linearLayoutManager);
                            ChooseListAdpter chooseListAdpter = new ChooseListAdpter(list_search,ChooseFriendActivity.this,intent_flag,intent_name);
                            chooseList.setAdapter(chooseListAdpter);

                        }else {
                            PersonInfoGsonBean personInfoGsonBean = response.body();
                            List<ChooseGroupBean> list_search = new ArrayList<ChooseGroupBean>();
                            ChooseGroupBean chooseGroupBean = new ChooseGroupBean();
                            chooseGroupBean.setFriend_id(String.valueOf(personInfoGsonBean.getId()));
                            chooseGroupBean.setFriend_age(String.valueOf(personInfoGsonBean.getAge()));
                            chooseGroupBean.setFriend_name(personInfoGsonBean.getUsername());
                            chooseGroupBean.setFriend_sex(personInfoGsonBean.getGender());
                            chooseGroupBean.setHead_image_path(personInfoGsonBean.getPortrait_url());
                            list_search.add(chooseGroupBean);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseFriendActivity.this);
                            chooseList.setLayoutManager(linearLayoutManager);
                            ChooseListAdpter chooseListAdpter = new ChooseListAdpter(list_search,ChooseFriendActivity.this,intent_flag,intent_name);
                            chooseList.setAdapter(chooseListAdpter);
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonInfoGsonBean> call, Throwable t) {
                        Log.d("call_choose onFailure()", String.valueOf(t));
                        Toast.makeText(ChooseFriendActivity.this,"连接失败，请检查网络后重试",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
