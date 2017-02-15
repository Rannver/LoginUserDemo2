package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.DateCheckUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowHeadImageUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowTvInfo;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rannver on 2017/1/26.
 */

public class PersonInfoEditActivity extends AppCompatActivity {
    @BindView(R.id.iv_edit_back)
    ImageView ivEditBack;
    @BindView(R.id.iv_edit_head)
    CircleImageView ivEditHead;
    @BindView(R.id.tv_info_edit_id)
    TextView tvInfoEditId;
    @BindView(R.id.tv_info_edit_name)
    TextView tvInfoEditName;
    @BindView(R.id.btu_edit_sex_boy)
    RadioButton btuEditSexBoy;
    @BindView(R.id.btu_edit_sex_girl)
    RadioButton btuEditSexGirl;
    @BindView(R.id.ed_edit_year)
    EditText edEditYear;
    @BindView(R.id.ed_edit_month)
    EditText edEditMonth;
    @BindView(R.id.ed_edit_day)
    EditText edEditDay;
    @BindView(R.id.ed_edit_address)
    EditText edEditAddress;
    @BindView(R.id.ed_edit_job)
    EditText edEditJob;
    @BindView(R.id.ed_edit_phone)
    EditText edEditPhone;
    @BindView(R.id.btu_edit_save)
    Button btuEditSave;

    private String intent_flag = null;
    private String intent_name = null;
    private String intent_id = null;
    private Uri image_head_uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info_edit_activity);
        ButterKnife.bind(this);

        Intent intent_login_flag = getIntent();
        intent_flag = intent_login_flag.getStringExtra("login_flag");
        intent_name = intent_login_flag.getStringExtra("login_name");
        intent_id = intent_login_flag.getStringExtra("user_id");
        Toast.makeText(PersonInfoEditActivity.this,intent_flag,Toast.LENGTH_SHORT).show();

        //点击返回
        ivEditBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(PersonInfoEditActivity.this,PersonInfoDetaiActivity.class);
                intent_back.putExtra("login_flag",intent_flag);
                intent_back.putExtra("login_name",intent_name);
                startActivity(intent_back);
            }
        });

        //显示来自数据库的手机号码等信息
        tvInfoEditId.setText(intent_id);
        tvInfoEditName.setText(intent_name);
        String sex = "";
        Date birthday = new Date();
        String address = "";
        String job = "";
        String phone = "";
        String head_image_path = "";

        List<PersonInfomation> info_list = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
        for (PersonInfomation personInfomation:info_list){
            sex = personInfomation.getGender();
            birthday = personInfomation.getBirthday();
            address = personInfomation.getAddress();
            job = personInfomation.getCareer();
            phone = String.valueOf(personInfomation.getPhone_number());
            head_image_path = personInfomation.getPortrait_url();
        }
        System.out.println("edit_Info:"+sex+"，"+birthday+"，"+address+"，"+job+"，"+phone+"，"+head_image_path);
        //可编辑信息显示
        ShowSex(sex);  //性别显示
        ShowBirthday(birthday);  //年龄显示
        ShowHeadImage(head_image_path);  //头像显示
        edEditAddress.setText(address);
        edEditJob.setText(job);
        edEditPhone.setText(phone);

        //性别判断
        ChangeSex();

        //点击设置头像事件
        ivEditHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopuWindowHeadImageUtil pop_util = new PopuWindowHeadImageUtil(PersonInfoEditActivity.this, PersonInfoEditActivity.this);
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

        //保存
        btuEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String year = edEditYear.getText().toString();
                String month = edEditMonth.getText().toString();
                String day = edEditDay.getText().toString();
                String address = edEditAddress.getText().toString();
                String job = edEditJob.getText().toString();
                String phone = edEditPhone.getText().toString();
                //检测信息完整度

                if (!CheckBirthday(year,month,day)){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("出生日期填写格式不正确，请检查后保存");
                }else if ((!btuEditSexGirl.isChecked()) && (!btuEditSexBoy.isChecked())){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("请选择你的性别");
                }else {
                    //上传至远程服务器、本地存储用户信息
                    LoadSave(year,month,day,address,job,phone);
                    //跳转至详细信息界面
                    Intent intent_detai = new Intent(PersonInfoEditActivity.this,PersonInfoDetaiActivity.class);
                    intent_detai.putExtra("login_flag",intent_flag);
                    intent_detai.putExtra("login_name",intent_name);
                    startActivity(intent_detai);
                }

            }
        });

    }

    //头像显示
    private void ShowHeadImage(String head_image_path) {
        //之后记得增加来自后台的url的解析显示
        if (head_image_path!=null){
            File file = new File(head_image_path);
            if (file.exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(head_image_path);
                ivEditHead.setImageBitmap(bitmap);
            }
        }
    }

    //本地数据库存储
    private void LoadSave(String year, String month, String day, String address, String job, String phone) {
        PersonInfomation personinfo = new PersonInfomation();
        if (image_head_uri!=null){
            personinfo.setPortrait_url(image_head_uri.getPath());
        }
        personinfo.setAddress(address);
        personinfo.setCareer(job);
        personinfo.setPhone_number(Long.parseLong(phone));
        personinfo.setBirthday(Setbirthday(year,month,day));
        personinfo.updateAll("username = ?",intent_name);
    }

    //设置出生日期Date
    private Date Setbirthday(String year,String month,String day){
        String date_str = year + "-" + month + "-" + day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date_signup = null;
        try {
            date_signup = sdf.parse(date_str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date_signup;
    }

    //性别判断
    private void ChangeSex() {

        btuEditSexBoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btuEditSexGirl.isChecked()) {
                    btuEditSexBoy.setChecked(true);
                    btuEditSexGirl.setChecked(false);
                }
            }
        });
        btuEditSexGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btuEditSexBoy.isChecked()) {
                    btuEditSexGirl.setChecked(true);
                    btuEditSexBoy.setChecked(false);
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

    //性别显示
    private void ShowSex(String sex){
        if (sex.equals("男")){
            btuEditSexBoy.setChecked(true);
        }else {
            btuEditSexGirl.setChecked(true);
        }
    }
    //生日显示
    private void ShowBirthday(Date birthday){
        String year = "";
        String month = "";
        String day = "";
        String str = BirthdayDateToStr(birthday);

        if (!str.equals("")){
            year = str.substring(0,str.indexOf("-"));
            month = str.substring(str.indexOf("-")+1,str.lastIndexOf("-"));
            day = str.substring(str.lastIndexOf("-")+1);
        }


        System.out.println("birthday:"+year+","+month+","+day);

        edEditYear.setText(year);
        edEditMonth.setText(month);
        edEditDay.setText(day);

    }
    private String BirthdayDateToStr(Date date){
        String string = "";

        if (date!=null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            string = sdf.format(date);
            System.out.println("detail_birthday:"+string);
        }
        return string;
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("requestCode:" + requestCode);
        if (requestCode == 1) {
            //相机设置的情况下
            startPhoneZoom(image_head_uri);
        } else if (requestCode == 2) {
            //照片设置的情况下
            if (data!= null){
                Cursor cursor = this.getContentResolver().query(data.getData(),
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                //游标移到第一位，即从第一位开始读取
                cursor.moveToFirst();
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                //调用系统裁剪
                startPhoneZoom(Uri.fromFile(new File(path)));
            }

        } else if (requestCode == 3) {
            //返回裁剪结果
            //设置裁剪返回的位图
            Bundle bundle = null;
            if (data!=null){
                bundle = data.getExtras();
            }
            if (bundle != null) {
                Bitmap bitmap = bundle.getParcelable("data");
                //将裁剪后得到的位图在组件中显示
                ivEditHead.setImageBitmap(bitmap);
            }
        } else {
            return;
        }
    }
}
