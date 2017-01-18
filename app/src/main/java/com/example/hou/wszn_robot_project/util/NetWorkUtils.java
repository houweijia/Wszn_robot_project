package com.example.hou.wszn_robot_project.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by hou on 2017/1/11.
 */

public class NetWorkUtils {
    public static String getWifiAddress(Context context){
        String address = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        address = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
        return address;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
