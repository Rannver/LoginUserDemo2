package com.example.rannver.loginuserdemo.Util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DisplayContext;
import android.icu.text.MessagePattern;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.rannver.loginuserdemo.R;

import java.io.File;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.security.KeyRep;

/**
 * Created by Rannver on 2017/1/17.
 * 参考网址
 * http://blog.csdn.net/qq_25193681/article/details/52262772
 * http://blog.csdn.net/qq_32314621/article/details/51212902
 */

public class PopuWindowHeadImageUtil extends AppCompatActivity {

    private Context PContext;
    private Activity PActicity;
    private File file;
    private Uri uri;
    private Button btu_photo ;
    private Button btu_picture ;
    private Button btu_cancel ;
    private onGetTypeClckListener listener;



    public PopuWindowHeadImageUtil(Context context, Activity activity){
        PContext = context;
        PActicity = activity;

    }

    public void ChangeheadImage() {

        View view = LayoutInflater.from(PContext).inflate(R.layout.popwindow_image,null);
        final PopupWindow pop = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT, true);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);

        //获取屏幕宽度
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setAnimationStyle(R.style.PopupAnimation);
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        //三个按钮的点击事件
        btu_photo = (Button) view.findViewById(R.id.btu_photo);
        btu_picture = (Button) view.findViewById(R.id.btu_picture);
        btu_cancel = (Button) view.findViewById(R.id.btu_cancel);

        //通过拍照设置头像
        btu_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                uri = Uri.fromFile(file);
                intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                PActicity.startActivityForResult(intent_photo,1);
                if (listener != null) {
                    listener.getImgUri(uri, file);
                }
                pop.dismiss();
            }
        });
        //通过选取图片设置头像
        btu_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用手机相册
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                PActicity.startActivityForResult(intent,2);
                pop.dismiss();

            }
        });
        //取消
        btu_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pop.dismiss();
            }
        });
    }

    public interface onGetTypeClckListener {
        void getImgUri(Uri ImgUri, File file);
    }

    public void setOnGetTypeClckListener(onGetTypeClckListener listener) {
        this.listener = listener;
    }

}
