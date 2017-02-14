package com.example.rannver.loginuserdemo.Adpter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rannver.loginuserdemo.Data.FriendGroupBean;
import com.example.rannver.loginuserdemo.R;

import java.io.File;
import java.util.List;

/**
 * Created by Rannver on 2017/2/8.
 * 好友列表的适配器
 */

public class FriendGroupAdpter extends RecyclerView.Adapter<FriendGroupAdpter.ViewHolder> {

    private List<FriendGroupBean> friend_list;
    private String friend_flag;

    public FriendGroupAdpter(List<FriendGroupBean> list, String flag){
        friend_list = list;
        friend_flag = flag;
        Log.d("Adpter_flag",flag);
    }

    @Override
    public FriendGroupAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if (friend_flag.equals("1")){
            //普通显示
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendgroup_common_item,null);
        }else {
            //老年界面显示
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendgroup_old_item,null);
        }

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final FriendGroupAdpter.ViewHolder holder, final int position) {

        //设置好友头像
        SetImageBitmap(holder,friend_list.get(position).getHead_image_path());
        //设置文字信息
        String name = friend_list.get(position).getFriend_remark()+"（"+friend_list.get(position).getFriend_name()+"）";
        String info = friend_list.get(position).getFriend_job()+"  "+friend_list.get(position).getFriend_sex()+"  "+friend_list.get(position).getFriend_age();
        holder.tv_name.setText(name);
        holder.tv_info.setText(info);
        //设置关心标识
//        String flag = friend_list.get(position).getFriend_flag();
//        switch (flag){
//            case "2":
//                holder.iv_care.setImageResource(R.drawable.whocare_icon);
//                break;
//            case "3":
//                holder.iv_care.setImageResource(R.drawable.carewho_icon);
//                break;
//            default:
//                break;
//        }

        //设置监听事件
        if (OnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getLayoutPosition();
                    OnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = holder.getLayoutPosition();
                    OnItemClickListener.onItemLongClick(holder.itemView,position);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return friend_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_head;
        private ImageView iv_care;
        private TextView tv_name;
        private TextView tv_info;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_head = (ImageView) itemView.findViewById(R.id.iv_friend_head);
            tv_name = (TextView) itemView.findViewById(R.id.tv_friend_name);
            tv_info = (TextView) itemView.findViewById(R.id.tv_friend_info);
            iv_care = (ImageView) itemView.findViewById(R.id.iv_friend_care);

        }
    }

    private void SetImageBitmap(FriendGroupAdpter.ViewHolder holder,String path){

        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                holder.iv_head.setImageBitmap(bitmap);
            }
        }
    }

    //点击事件
    public interface OnItemClickListener{
        void  onItemClick(View view,int position);
        void  onItemLongClick(View view,int position);
    }
    private OnItemClickListener OnItemClickListener;

    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        OnItemClickListener =onItemClickListener;
    }

}
