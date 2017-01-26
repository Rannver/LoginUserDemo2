package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.DateCheckUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowHeadImageUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowTvInfo;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/17.
 */
public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.ed_signup_id)
    EditText edSignupname;
    @BindView(R.id.ed_signup_pwd1)
    EditText edSignupPwd1;
    @BindView(R.id.ed_signup_pwd2)
    EditText edSignupPwd2;
    @BindView(R.id.ed_signup_address)
    EditText edSignupAddress;
    @BindView(R.id.ed_signup_job)
    EditText edSignupJob;
    @BindView(R.id.iv_signup_back)
    ImageView ivSignupBack;
    @BindView(R.id.ed_signup_phone)
    EditText edSignupPhone;
    @BindView(R.id.btu_signup)
    Button btuSignup;
    @BindView(R.id.Rbtu_sex_boy)
    RadioButton RbtuSexBoy;
    @BindView(R.id.Rbtu_sex_girl)
    RadioButton RbtuSexGirl;
    @BindView(R.id.ed_signup_year)
    EditText edSignupYear;
    @BindView(R.id.ed_signup_month)
    EditText edSignupMonth;
    @BindView(R.id.ed_signup_day)
    EditText edSignupDay;


    private Uri image_head_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);

        //返回上一页的点击事件
        ivSignupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent_back);
            }
        });

        //设置头像的点击事件
        ivHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopuWindowHeadImageUtil pop_util = new PopuWindowHeadImageUtil(SignUpActivity.this, SignUpActivity.this);
                pop_util.ChangeheadImage();
                pop_util.setOnGetTypeClckListener(new PopuWindowHeadImageUtil.onGetTypeClckListener() {
                    @Override
                    public void getImgUri(Uri ImgUri, File file) {
                        image_head_uri = ImgUri;
                        System.out.println("uri:" + image_head_uri);
                    }
                });
            }
        });

        //性别单选框的点击效果判断
        ChangeSex();

        //注册按钮的点击效果
        btuSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //用户信息
                String user_adress = edSignupAddress.getText().toString();
                String user_job = edSignupJob.getText().toString();
                String user_name = edSignupname.getText().toString();
                String user_phone = edSignupPhone.getText().toString();
                String user_pwd1 = edSignupPwd1.getText().toString();
                String user_pwd2 = edSignupPwd2.getText().toString();
                String year = edSignupYear.getText().toString();
                String month = edSignupMonth.getText().toString();
                String day = edSignupDay.getText().toString();
                String user_sex;
                //未正常注册时的提示语
                String info;

                //检测信息完整度
                if (!user_pwd1.equals(user_pwd2)) {
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(SignUpActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("两次密码输入不一致，请更改后注册");
                } else if (user_name.equals("") || user_pwd1.equals("") || user_pwd2.equals("") || user_phone.equals("")) {
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(SignUpActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("有必填信息未填写，请填写完整资料后注册");
                } else if ((!RbtuSexGirl.isChecked()) && (!RbtuSexBoy.isChecked())) {
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(SignUpActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("请选择你的性别");
                } else if (!(user_phone.length() == 11)) {
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(SignUpActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("手机号码格式不正确，请检查你的手机号码后注册");
                } else if (!CheckBirthday(year,month,day)) {
                    System.out.println("CheckBirthday()" + CheckBirthday(year,month,day));
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(SignUpActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("出生日期填写格式不正确，请检查后注册");
                } else {
                    //上传至远程服务器、本地存储用户信息
                    //跳转至个人信息界面
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }


            }
        });

    }


    //检测出生日期信息是否正确
    private boolean CheckBirthday(String year,String month,String day) {

        DateCheckUtil dateCheckUtil = new DateCheckUtil();

        //设置出生日期的Date类
        Date date_signup = new Date();
        String date_str = year + "-" + month + "-" + day;
        SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date_signup = sdf_date.parse(date_str);
            if (!dateCheckUtil.CheckDateFromDay(year, month, day)) {
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    //裁剪器
    private void startPhoneZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //是否可裁剪
        intent.putExtra("corp", "true");
        //裁剪器高宽比
        intent.putExtra("aspectY", 1);
        intent.putExtra("aspectX", 1);
        //设置裁剪框高宽
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        //返回数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    //性别判断
    public void ChangeSex() {

        RbtuSexBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RbtuSexGirl.isChecked()) {
                    RbtuSexBoy.setChecked(true);
                    RbtuSexGirl.setChecked(false);
                }
            }
        });
        RbtuSexGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RbtuSexBoy.isChecked()) {
                    RbtuSexGirl.setChecked(true);
                    RbtuSexBoy.setChecked(false);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode:" + requestCode);
        if (requestCode == 1) {
            //相机设置的情况下
            startPhoneZoom(image_head_uri);
        } else if (requestCode == 2) {
            //照片设置的情况下
            Cursor cursor = this.getContentResolver().query(data.getData(),
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            //游标移到第一位，即从第一位开始读取
            cursor.moveToFirst();
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
            //调用系统裁剪
            startPhoneZoom(Uri.fromFile(new File(path)));

        } else if (requestCode == 3) {
            //返回裁剪结果
            //设置裁剪返回的位图
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                Bitmap bitmap = bundle.getParcelable("data");
                //将裁剪后得到的位图在组件中显示
                ivHead.setImageBitmap(bitmap);
            }
        } else {
            return;
        }
    }

}
