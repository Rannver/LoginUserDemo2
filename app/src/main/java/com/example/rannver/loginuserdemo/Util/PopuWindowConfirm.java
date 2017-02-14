package com.example.rannver.loginuserdemo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.rannver.loginuserdemo.BuildConfig;
import com.example.rannver.loginuserdemo.Data.PersonFriend;
import com.example.rannver.loginuserdemo.Data.PersonInfomation;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.UI.PersonInfoActivity;

import org.litepal.crud.DataSupport;

import java.util.List;

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

    private void DeleteFriend(String name,String friend_id) {
        List<PersonInfomation> infos = DataSupport.where("username = ?",name).find(PersonInfomation.class);
        int user_id = 0;
        for (PersonInfomation person:infos){
            user_id = person.getId();
        }
        DataSupport.deleteAll(PersonFriend.class,"personinfomation_id = ? and friend_id = ?", String.valueOf(user_id),friend_id);
    }

}
