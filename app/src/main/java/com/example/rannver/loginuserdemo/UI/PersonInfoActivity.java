package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;

import org.litepal.crud.DataSupport;

import java.io.File;
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

        Toast.makeText(PersonInfoActivity.this,intent_flag+"，"+intent_name,Toast.LENGTH_SHORT).show();

        //个人信息页面显示
        List<PersonInfomation> info_list = DataSupport.where("user_name = ?",intent_name).find(PersonInfomation.class);
        String name = "";
        String sex = "";
        String job = "";
        String head_image_path = "";
        for (PersonInfomation personInfomation:info_list){
            name = personInfomation.getUser_name();
            sex = personInfomation.getUser_sex();
            job = personInfomation.getJob();
            head_image_path = personInfomation.getUser_image_head();
        }
        System.out.println("Info:"+name+" "+sex+" "+job+" "+head_image_path);
        //设置文字信息
        infoUsername.setText(name);
        infoUsersex.setText(sex);
        infoUserjob.setText(job);
        //设置图片信息
        if (head_image_path!=null){
            File file = new File(head_image_path);
            if (file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(head_image_path);
                ivInfoHead.setImageBitmap(bitmap);
            }
        }


        //好友页面显示
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
                intent_detai.putExtra("login_name",intent_name);
                startActivity(intent_detai);
            }
        });



    }
}
