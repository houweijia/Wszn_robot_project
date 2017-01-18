package com.example.hou.wszn_robot_project.util;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.example.hou.wszn_robot_project.R;

/**
 * Created by hou on 2017/1/3.
 */



public class MyExpression {
    /**
     * @param iv 传入一个imageview
     * @param boo 是否播放一次 true——是
     */
    public static void showExpression(ImageView iv, boolean boo, int imageResource){
        iv.setImageResource(imageResource);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        //animationDrawable.setOneShot(boo);
        animationDrawable.setOneShot(boo);
        animationDrawable.start();
    }
}
