package com.example.hou.wszn_robot_project.broadcast;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by hou on 2017/1/10.
 */

public class UDPSocketBroadCast {
    private static final String BROADCAST_IP = "224.0.0.1";
    private static final int BROADCAST_PORT = 8681;
    private static byte[] sendData;
    private boolean isStop = false;
    private static UDPSocketBroadCast broadCast = new UDPSocketBroadCast();
    private MulticastSocket mSocket = null;
    private InetAddress address = null;
    private DatagramPacket dataPacket;
    private UDPSocketBroadCast() {
    }
    /**
     * 单例
     *
     * @return
     */
    public static UDPSocketBroadCast getInstance() {
        if (broadCast == null) {
            broadCast = new UDPSocketBroadCast();
        }
        return broadCast;
    }

    /**
     * 开始发送广播
     *
     * @param ip
     */
    public void startUDP(String ip, int port) {
        sendData = ("xxxx" + "-" + ip + "-" + port).getBytes();
        Log.e("main", ip+";"+port);
        Log.e("main","开启");
        new Thread(UDPRunning).start();
    }

    /**
     * 停止广播
     */
    public void stopUDP() {
        isStop = true;
        destroy();
    }

    /**
     * 销毁缓存的数据
     */
    public void destroy() {
        broadCast = null;
        mSocket = null;
        address = null;
        dataPacket = null;
        sendData = null;
    }

    /**
     * 创建udp数据
     */
    private void CreateUDP() {
        try {
            mSocket = new MulticastSocket(BROADCAST_PORT);
            mSocket.setTimeToLive(1);// 广播生存时间0-255
            address = InetAddress.getByName(BROADCAST_IP);
            mSocket.joinGroup(address);//加入广播接收组
            dataPacket = new DatagramPacket(sendData, sendData.length, address,
                    BROADCAST_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 5秒发送一次广播
     */
    private Runnable UDPRunning = new Runnable() {

        @Override
        public void run() {
            while (!isStop) {
                if (mSocket != null) {
                    try {
                        mSocket.send(dataPacket);
                        Thread.sleep(5 * 1000);// 发送一次停5秒
                        Log.e("main","5s");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    CreateUDP();
                }
            }
        }
    };
}
