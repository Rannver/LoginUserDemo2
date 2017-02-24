package com.example.rannver.loginuserdemo.Adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.dbTable.PersonFriend;
import com.example.rannver.loginuserdemo.Data.dbTable.PersonInfomation;
import com.example.rannver.loginuserdemo.Data.gsonBean.FriendGsonBean;
import com.example.rannver.loginuserdemo.Data.listBean.ChooseGroupBean;
import com.example.rannver.loginuserdemo.R;
import com.example.rannver.loginuserdemo.UI.FriendSettingActivity;
import com.example.rannver.loginuserdemo.WebApi.GetFriendInfoApi;
import com.example.rannver.loginuserdemo.WebService.GetFriendInfoService;
import com.squareup.picasso.Picasso;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Rannver on 2017/2/11.
 * 好友查询列表List的Adpter
 */

public class ChooseListAdpter extends RecyclerView.Adapter<ChooseListAdpter.ViewHolder>{

    private List<ChooseGroupBean> Choose_List = null;
    private Activity Activity_choose;
    private Context Context_choose;
    private String intent_flag = null;
    private String intent_name = null;

    public ChooseListAdpter(List<ChooseGroupBean> list,Activity activity,String login_flag,String login_name){
        Choose_List = list;
        Activity_choose = activity;
        intent_flag = login_flag;
        intent_name = login_name;
    }

    @Override
    public ChooseListAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choosefriend_item,null);
        ViewHolder viewHolder = new ViewHolder(view);
        Context_choose = parent.getContext();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ChooseListAdpter.ViewHolder holder, final int position) {

        //设置头像
        SetImageBitmap(holder,Choose_List.get(position).getHead_image_path());
        //设置文字信息
        String name = Choose_List.get(position).getFriend_name()+"（"+Choose_List.get(position).getFriend_id()+"）";
        String info = Choose_List.get(position).getFriend_sex()+"，"+Choose_List.get(position).getFriend_age();
        holder.tv_name.setText(name);
        holder.tv_info.setText(info);

        //好友添加事件
        holder.btu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Choose_List.get(position).getFriend_name();
                String id  = Choose_List.get(position).getFriend_id();
                int flag = CheckRepair(name, Integer.parseInt(id));
                if (flag==1){
                    //跳转至好友关系和备注设置界面
                    Intent intent_set = new Intent(Activity_choose, FriendSettingActivity.class);
                    intent_set.putExtra("login_flag",intent_flag);
                    intent_set.putExtra("login_name",intent_name);
                    intent_set.putExtra("friend_id",Choose_List.get(position).getFriend_id());//返回好友id
                    intent_set.putExtra("friend_flag","add");
                    Context_choose.startActivity(intent_set);
                }else if(flag==2){
                    Toast.makeText(Activity_choose,"不能添加自己为好友",Toast.LENGTH_SHORT).show();
                }else if (flag==3){
                    Toast.makeText(Activity_choose,"该用户已是您的好友",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Activity_choose,"好友添加异常",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return Choose_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_head;
        private TextView  tv_name;
        private TextView  tv_info;
        private Button    btu_add;
        private LinearLayout lin_info;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_choose_head);
            tv_name = (TextView) itemView.findViewById(R.id.choose_item_name);
            tv_info = (TextView) itemView.findViewById(R.id.choose_item_info);
            btu_add = (Button) itemView.findViewById(R.id.btu_choose_add);
            lin_info = (LinearLayout) itemView.findViewById(R.id.lin_info);
        }
    }

    private void SetImageBitmap(ChooseListAdpter.ViewHolder holder,String path){

        if (path != null) {
            Picasso.with(Activity_choose).load(path).into(holder.iv_head);
        }
    }

    /*检测需要添加的好友是否是自己或是已添加好友
    flag为1表示可添加
    flag为2表示该要添加的用户是本身
    flag为3表示要添加的用户已经是该用户的好友
    */
    private int CheckRepair(String name,int id) {
        int flag = 1;

        if (name.equals(intent_name)){
            //检测到要添加的用户是自己本身，返回false
            flag = 2;
        }
        //获取用户好友列表
        List<PersonInfomation> infos = DataSupport.where("username = ?",intent_name).find(PersonInfomation.class);
        List<PersonFriend> friends = null;
        for (PersonInfomation personInfomation :infos){
            friends = personInfomation.getFriend_list();
        }
        for (PersonFriend personFriend:friends){
            if (personFriend.getFriend_id()==id){
                flag=3;
                break;
            }
        }
        return flag;
    }
}
