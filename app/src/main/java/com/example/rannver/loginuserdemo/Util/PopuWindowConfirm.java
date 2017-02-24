package com.example.rannver.loginuserdemo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.UI.PersonInfoActivity;
import com.example.rannver.loginuserdemo.WebApi.DeleteFriendApi;
import com.example.rannver.loginuserdemo.WebApi.RemoveCareApi;
import com.example.rannver.loginuserdemo.WebApi.SetCareApi;
import com.example.rannver.loginuserdemo.WebApi.ShowBeCareApi;
import com.example.rannver.loginuserdemo.WebService.DeleteFriendService;
import com.example.rannver.loginuserdemo.WebService.RemoveCareService;
import com.example.rannver.loginuserdemo.WebService.SetCareService;
import com.example.rannver.loginuserdemo.WebService.ShowBeCareService;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.typechange.TextOrm;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rannver on 2017/2/15.
 */

public class PopuWindowConfirm extends PopupWindow {

    private Context PContext;
    private Activity PActivity;
    private View PView;
    private PopupWindow pop;
    private String intent_name = "";
    private String intent_flag = "";

    public PopuWindowConfirm(Context context, Activity activity,String intent_name,String intent_flag){
        PContext = context;
        PActivity = activity;
        this.intent_name = intent_name;
        this.intent_flag = intent_flag;
    }

    private void initView() {
        View view = LayoutInflater.from(PContext).inflate(R.layout.popuwindow_confirm_info,null);
        PView = view;
        pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);

        //获取屏幕宽度
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setAnimationStyle(R.style.PopupAnimation);
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    public void DeleteFriendPopu(final String name, String remark, final String friend_id, final String username){
        /*
        name:好友用户名
        remark:好友备注
        friend_id:好友id
        username:用户名
         */

        initView();
        //提示信息更新
        String str = "";
        str = "是否解除好友"+name+"("+remark+")"+"与你的关系？";
        TextView tv_info = (TextView) PView.findViewById(R.id.tv_comfirm_info);
        tv_info.setText(str);

        Button btu_comfirm = (Button) PView.findViewById(R.id.btu_confirm_left);
        Button btu_cancel = (Button) PView.findViewById(R.id.btu_confirm_right);

        //点击确认删除事件
        btu_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteFriend(username,friend_id);
                Intent intent_back = new Intent(PActivity, PersonInfoActivity.class);
                intent_back.putExtra("login_name",intent_name);
                intent_back.putExtra("login_flag",intent_flag);
                PContext.startActivity(intent_back);

            }
        });

        //点击取消事件
        btu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }

    //设置特别关心好友
    public void SetCareFriend (final String name, String remark, final String friend_id, final String username){
        /*
        name:好友用户名
        remark:好友备注
        friend_id:好友id
        username:用户名
         */
        initView();
        //提示信息更新
        String str = "";
        str = "是否将好友"+name+"（"+remark+"）"+"设置为特别关心？";
        TextView tv_info = (TextView) PView.findViewById(R.id.tv_comfirm_info);
        tv_info.setText(str);

        Button btu_comfirm = (Button) PView.findViewById(R.id.btu_confirm_left);
        Button btu_cancel = (Button) PView.findViewById(R.id.btu_confirm_right);

        //点击确认添加特别关心事件
        btu_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCare(username,friend_id);
            }
        });
        //点击取消事件
        btu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });

    }

    //取消特别关心好友
    public void CancelCareFriend(final String name, String remark, final String friend_id, final String username){
       /*
        name:好友用户名
        remark:好友备注
        friend_id:好友id
        username:用户名
         */

        initView();
        //提示信息更新
        String str = "";
        str = "是否解除与好友"+name+"（"+remark+"）"+"的特别关心关系？";
        TextView tv_info = (TextView) PView.findViewById(R.id.tv_comfirm_info);
        tv_info.setText(str);

        Button btu_comfirm = (Button) PView.findViewById(R.id.btu_confirm_left);
        Button btu_cancel = (Button) PView.findViewById(R.id.btu_confirm_right);
        //点击取消特别关心事件
        btu_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<PersonInfomation> infos = DataSupport.where("username = ?",username).find(PersonInfomation.class);
                int user_id = 0;
                for (PersonInfomation person : infos){
                    user_id = person.getUser_id();
                }
                DeleteCare(user_id,friend_id);
            }
        });
        //点击取消事件
        btu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }

    //删除特别关心关系
    private void DeleteCare(final int user_id, String friend_id) {

        //后台移除特别关心关系
        RemoveCareApi removeCareApi = new RemoveCareApi();
        RemoveCareService removeCareService = removeCareApi.getService();
        Call<String> call_remove = removeCareService.getState(user_id);
        call_remove.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //本地解除关系，用户表删除特别关心
                PersonInfomation personInfomation_user = new PersonInfomation();
                PersonInfomation personInfomation_friend = new PersonInfomation();
                personInfomation_user.setToDefault("care");
                personInfomation_user.updateAll("id = ?", String.valueOf(user_id));

                Intent intent_back = new Intent(PActivity,PersonInfoActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent_back.putExtra("login_flag",intent_flag);
                intent_back.putExtra("login_name",intent_name);
                PContext.startActivity(intent_back);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PActivity,"连接失败，请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        });

        System.out.println("delete:"+"特别关心关系已解除");

    }

    //添加特别关心好友
    private void AddCare(String username, String friend_id) {
        //检测用户是否已有特别关心的好友
        boolean flag_care = CheckCare(username);
        CheckMutual(username,friend_id,flag_care);
    }

    //添加特别关心
    private void AddLocalCare(String user_id, String friend_id) {

        /*
        be_addperson:被添加的id
        addperson:添加进去的id
        flag:判断是添加特别关心还是被特别关心
         */

        PersonInfomation person_care = new PersonInfomation();
        PersonInfomation person_becare = new PersonInfomation();
        //用户添加特别关心id
        person_care.setCare(Integer.parseInt(friend_id));
        person_care.updateAll("id = ?", String.valueOf(user_id));

    }

    //检测好友是否已被特别关心
    private void CheckBeCare(final String user_id, final String friend_id) {
        ShowBeCareApi showBeCareApi = new ShowBeCareApi();
        ShowBeCareService showBeCareService = showBeCareApi.getService();
        Call<Integer> call_becare = showBeCareService.getBeCare(Integer.parseInt(friend_id));
        call_becare.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body()==0){
                    //该用户没有特别关心的好友
                    AddCloudCare(user_id,friend_id);
                }else {
                    Toast.makeText(PActivity,"该用户已被特别关心",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.d("becare_onFailure", String.valueOf(t));
            }
        });
    }

    //添加特别关心到后台
    private void AddCloudCare(final String user_id, final String friend_id) {
        SetCareApi careApi = new SetCareApi();
        SetCareService careService = careApi.getService();
        Call<String> call_care = careService.getState(Integer.parseInt(user_id),Integer.parseInt(friend_id));
        call_care.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("false")){
                    Toast.makeText(PActivity,"该用户已被特别关心",Toast.LENGTH_SHORT).show();
                }else {
                    AddLocalCare(user_id,friend_id);
                    Intent intent_back = new Intent(PActivity,PersonInfoActivity.class);
                    intent_back.putExtra("login_name",intent_name);
                    intent_back.putExtra("login_flag",intent_flag);
                    PContext.startActivity(intent_back);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PActivity,"添加失败，请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //检测用户是否已有特别关心的好友，已有返回false，否则返回true
    private boolean CheckCare(String username) {
        boolean flag = false;

        List<PersonInfomation> infos = DataSupport.where("username = ?",username).find(PersonInfomation.class);
        int care_id = -1;
        for (PersonInfomation person:infos) {
            care_id = person.getCare();
        }

        if (care_id==0){
            flag = true; //该用户并未设置特别关心的好友
        }

        return flag;
    }

    //检测是否相互为好友，是相互好友返回true，否则返回flase
    private void CheckMutual(String username, String friend_id,boolean flag_care) {;
        boolean flag_user = false;

        //检测用户好友列表
        List<PersonInfomation> infos_user = DataSupport.where("username = ?",username).find(PersonInfomation.class);
        List<PersonFriend> user_friends = null;
        int user_id  = 0;
        for (PersonInfomation person:infos_user){
            user_friends = person.getFriend_list();
            user_id = person.getUser_id();
        }
        for (PersonFriend friend:user_friends){
            String id = String.valueOf(friend.getFriend_id());
            if (id==friend_id){
                flag_user = true;
                break;
            }
        }

        //检测好友好友列表
        if (flag_user){
            if (flag_care){
                CheckBeCare(String.valueOf(user_id),friend_id);
            }else {
                Toast.makeText(PActivity,"你已有特别关心的好友",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(PActivity,"该用户不是您的好友",Toast.LENGTH_SHORT).show();
        }
    }




    //删除好友操作
    private void DeleteFriend(final String name, final String friend_id) {
        List<PersonInfomation> infos = DataSupport.where("username = ?",name).find(PersonInfomation.class);
        int user_id = 0;
        for (PersonInfomation person:infos){
            user_id = person.getUser_id();
        }
        DeleteFriendApi deleteApi = new DeleteFriendApi();
        DeleteFriendService deleteService = deleteApi.getService();
        Call<String> call_delete = deleteService.getState(String.valueOf(user_id),friend_id);
        call_delete.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                List<PersonInfomation> infos = DataSupport.where("username = ?",name).find(PersonInfomation.class);
                int key_id = 0;
                String care_id = "";
                String becare_id = "";
                for (PersonInfomation person:infos){
                    key_id = person.getId();
                    care_id = String.valueOf(person.getCare());
                    becare_id = String.valueOf(person.getBecare());
                }
                //删除好友成功，本地数据库删除好友关系
                LocalDelete(name,key_id,friend_id,care_id,becare_id);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(PActivity,"删除好友失败,请检查网络设置",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //本地数据库删除好友
    private void LocalDelete(String name,int key_id, String friend_id, String care_id, String becare_id) {
        DataSupport.deleteAll(PersonFriend.class,"personinfomation_id = ? and friend_id = ?", String.valueOf(key_id),friend_id);
        if (care_id.equals(friend_id)){

            //解除关系，用户表删除特别关心
            PersonInfomation personInfomation_user = new PersonInfomation();
            PersonInfomation personInfomation_friend = new PersonInfomation();
            personInfomation_user.setToDefault("care");
            personInfomation_user.updateAll("username = ?",name);
            //好友的用户表删除被特别关心
            personInfomation_friend.setToDefault("becare");
            personInfomation_friend.updateAll("id = ?",friend_id);
            System.out.println("delete:"+"特别关心关系已解除");

        }else if (becare_id.equals(friend_id)){
            //解除关系，用户表删除被特别关心
            PersonInfomation personInfomation = new PersonInfomation();
            PersonInfomation personInfomation_friend = new PersonInfomation();
            personInfomation.setToDefault("becare");
            personInfomation.updateAll("username = ?",name);
            //好友的用户表删除特别关心
            personInfomation_friend.setToDefault("bcare");
            personInfomation_friend.updateAll("id = ?",friend_id);
            System.out.println("delete:"+"特别关心关系已解除");

        }
    }

}
