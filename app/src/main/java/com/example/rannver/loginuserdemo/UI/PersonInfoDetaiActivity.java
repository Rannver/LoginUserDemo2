package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.Data.dbTable.SaveUser;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/26.
 */

public class PersonInfoDetaiActivity extends AppCompatActivity {

    @BindView(R.id.btu_info_detai_edit)
    Button btuInfoDetaiEdit;
    @BindView(R.id.iv_detai_head)
    CircleImageView ivDetaiHead;
    @BindView(R.id.tv_info_detai_id)
    TextView tvInfoDetaiId;
    @BindView(R.id.tv_info_detai_name)
    TextView tvInfoDetaiName;
    @BindView(R.id.tv_info_detai_sex)
    TextView tvInfoDetaiSex;
    @BindView(R.id.tv_info_detai_year)
    TextView tvInfoDetaiYear;
    @BindView(R.id.tv_info_detai_address)
    TextView tvInfoDetaiAddress;
    @BindView(R.id.tv_info_detai_job)
    TextView tvInfoDetaiJob;
    @BindView(R.id.tv_info_detai_phone)
    TextView tvInfoDetaiPhone;
    @BindView(R.id.iv_detai_back)
    ImageView ivDetaiBack;
    @BindView(R.id.btu_sigup_back)
    Button btuSigupBack;

    private String intent_flag = null;
    private String intent_name = null;
    private String id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_detai_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        intent_name = intent_login_flag.getStringExtra("login_name");
        Toast.makeText(PersonInfoDetaiActivity.this,intent_flag+","+intent_name,Toast.LENGTH_SHORT).show();

        //个人信息页面显示
        List<PersonInfomation> info_list = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);

        id ="";
        String name = "";
        String sex = "";
        Date birthday = new Date();
        String address = "";
        String job = "";
        String phone = "";
        String head_image_path = "";

        for (PersonInfomation personInfomation:info_list){
            id = String.valueOf(personInfomation.getUser_id());
            name = personInfomation.getUsername();
            sex = personInfomation.getGender();
            birthday = personInfomation.getBirthday();
            System.out.println("detail_birthday:"+personInfomation.getBirthday());
            address = personInfomation.getAddress();
            job = personInfomation.getCareer();
            phone = String.valueOf(personInfomation.getPhone_number());
            head_image_path = personInfomation.getPortrait_url();
        }
        System.out.println("Info:"+id+"，"+name+"，"+sex+"，"+birthday+"，"+address+"，"+job+"，"+phone+"，"+head_image_path);
        tvInfoDetaiId.setText(id);
        tvInfoDetaiName.setText(name);
        tvInfoDetaiSex.setText(sex);
        tvInfoDetaiYear.setText(BirthdayDateToStr(birthday));//这里需要做进一步的日期显示
        tvInfoDetaiAddress.setText(address);
        tvInfoDetaiJob.setText(job);
        tvInfoDetaiPhone.setText(phone);
        //设置图片信息
        //本地路径方式，如果是后台的Url需要用picasso
        if (head_image_path != null) {
            Picasso.with(PersonInfoDetaiActivity.this).load(head_image_path).into(ivDetaiHead);
        }
        //点击返回
        ivDetaiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(PersonInfoDetaiActivity.this, PersonInfoActivity.class);
                intent_back.putExtra("login_flag",intent_flag);
                intent_back.putExtra("login_name",intent_name);
                startActivity(intent_back);
            }
        });
        //点击编辑个人信息
        btuInfoDetaiEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_edit = new Intent(PersonInfoDetaiActivity.this,PersonInfoEditActivity.class);
                intent_edit.putExtra("login_flag",intent_flag);
                intent_edit.putExtra("login_name",intent_name);
                intent_edit.putExtra("user_id",id);
                startActivity(intent_edit);
            }
        });
        //点击退出登录事件
        btuSigupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //更新SaveUser表
                SaveUserTable();

                Intent intent_signup_back = new Intent(PersonInfoDetaiActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent_signup_back.putExtra("login_back",intent_name);
                startActivity(intent_signup_back);
            }
        });

    }

    //更新SaveUser表
    private void SaveUserTable() {
        SaveUser user = new SaveUser();
        user.setToDefault("staus");
        user.updateAll("username = ?",intent_name);
    }

    private String BirthdayDateToStr(Date date){
        String string;

        if (date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            string = sdf.format(date);
            System.out.println("detail_birthday:"+string);
        }else {
            string = "#/A";
        }
        return string;
    }
}
