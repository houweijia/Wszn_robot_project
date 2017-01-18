package com.example.hou.wszn_robot_project.util;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import java.net.URL;

/**
 * Created by hou on 2017/1/3.
 */

public class AppUtils {
    Context context;
    /**
     *
     * @param wv 传入webview控件
     * @param str 传入需要加载的网页
     */
    public static void showWebView(WebView wv,String str){
        wv.loadUrl(str);

    }

    public static void play(Context context,int mediaResource){
        MediaPlayer player  =   MediaPlayer.create(context,mediaResource);
        player.start();
    }

}
