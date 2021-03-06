package com.example.rannver.loginuserdemo.UI;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.Util.CircleImageView;
import com.example.rannver.loginuserdemo.Util.DateCheckUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowHeadImageUtil;
import com.example.rannver.loginuserdemo.Util.PopuWindowTvInfo;
import com.example.rannver.loginuserdemo.WebApi.ChangePortraitApi;
import com.example.rannver.loginuserdemo.WebApi.ChangeUserInfoApi;
import com.example.rannver.loginuserdemo.WebService.ChangeFriendInfoService;
import com.example.rannver.loginuserdemo.WebService.ChangePortraitService;
import com.example.rannver.loginuserdemo.WebService.ChangeUserInfoService;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                String sex ;
                if (btuEditSexBoy.isChecked()){
                    sex = btuEditSexBoy.getText().toString();
                }else {
                    sex = btuEditSexGirl.getText().toString();
                }
                //检测信息完整度

                if (!CheckBirthday(year,month,day)){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("出生日期填写格式不正确，请检查后保存");
                }else if ((!btuEditSexGirl.isChecked()) && (!btuEditSexBoy.isChecked())){
                    PopuWindowTvInfo popuWindowTvInfo = new PopuWindowTvInfo(PersonInfoEditActivity.this);
                    popuWindowTvInfo.ChangepopuInfo("请选择你的性别");
                }else {
                    //上传至远程服务器、本地存储用户信息
                    CloudSave(year,month,day,address,job,phone,sex);
                }

            }
        });

    }

    //更改的用户数据保存到后台
    private void CloudSave(final String year, final String month, final String day, final String address, final String job, final String phone, final String sex) {
        DateCheckUtil datecheck = new DateCheckUtil();
        ChangeUserInfoApi changeApi = new ChangeUserInfoApi();
        ChangeUserInfoService changeService = changeApi.getService();
        Call<String> call_change = changeService.getState(Integer.parseInt(intent_id),datecheck.CalAge(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day)),Long.parseLong(phone),intent_name,job,address,sex,Setbirthday(year,month,day).getTime());
        call_change.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body()!=null){
                    if (response.body().equals("true")){
                        if (image_head_uri!=null){
                            CloudPortraitSave();
                        }
                        LoadSave(year,month,day,address,job,phone,sex);
                        Intent intent_detai = new Intent(PersonInfoEditActivity.this,PersonInfoDetaiActivity.class);
                        intent_detai.putExtra("login_flag",intent_flag);
                        intent_detai.putExtra("login_name",intent_name);
                        startActivity(intent_detai);
                    }
                }else {
                    System.out.println("response.body():"+response.body()+" by edit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PersonInfoEditActivity.this,"连接失败，请检查网络连接设置",Toast.LENGTH_SHORT).show();
                Log.d("edit_onFailure", String.valueOf(t));
            }
        });
    }

    //云端头像存储
    private void CloudPortraitSave() {
        ChangePortraitApi changeApi = new ChangePortraitApi();
        ChangePortraitService changeService = changeApi.getService();
        File file = new File(image_head_uri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("portrait",file.getName(),requestFile);
        RequestBody username = RequestBody.create(null,intent_name);
        Call<String> call_change = changeService.getState(username,body);
        call_change.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("response.body():"+response.body()+"   byProtrait"+" "+intent_name);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PersonInfoEditActivity.this,"连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
                Log.d("edit_onFailureByPor", String.valueOf(t));
            }
        });
    }

    //头像显示
    private void ShowHeadImage(String head_image_path) {
        if (image_head_uri!=null){
            head_image_path = image_head_uri.getPath();
        }
        if (head_image_path != null) {
            File file = new File(head_image_path);
            if (file.exists()){
                Picasso.with(PersonInfoEditActivity.this).load(file).into(ivEditHead);
            }else {
                Picasso.with(PersonInfoEditActivity.this).load(head_image_path).into(ivEditHead);
            }
        }
    }

    //本地数据库存储
    private void LoadSave(String year, String month, String day, String address, String job, String phone,String sex) {
        PersonInfomation personinfo = new PersonInfomation();
        if (image_head_uri!=null){
            personinfo.setPortrait_url(image_head_uri.getPath());
        }
        personinfo.setAddress(address);
        personinfo.setCareer(job);
        personinfo.setPhone_number(Long.parseLong(phone));
        personinfo.setBirthday(Setbirthday(year,month,day));
        personinfo.setGender(sex);
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
                Uri uri = Uri.fromFile(new File(path));
                image_head_uri = uri;
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
