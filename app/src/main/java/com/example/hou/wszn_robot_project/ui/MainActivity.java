package com.example.hou.wszn_robot_project.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.hou.wszn_robot_project.R;
import com.example.hou.wszn_robot_project.broadcast.UDPSocketBroadCast;
import com.example.hou.wszn_robot_project.server.ServersSocket;
import com.example.hou.wszn_robot_project.service.SocketService;
import com.example.hou.wszn_robot_project.util.MyExpression;
import com.example.hou.wszn_robot_project.util.NetWorkUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView leftEye;
    private ImageView rightEye;
    private ImageView mouth;
    private ImageView iv;
    private Button btn,btn_broadcast;
    private Context context;
    private ServersSocket serversSocket = ServersSocket.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        WifiManager manager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        WifiManager.MulticastLock lock= manager.createMulticastLock("test wifi");
        lock.acquire();
        initView();



    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn_broadcast = (Button) findViewById(R.id.btn_broadcast);
        btn.setOnClickListener(this);
        btn_broadcast.setOnClickListener(this);
        leftEye = (ImageView) findViewById(R.id.left_eye);
        rightEye = (ImageView) findViewById(R.id.right_eye);
        mouth = (ImageView) findViewById(R.id.mouth);
        Intent intent = new Intent(this, SocketService.class);
        startService(intent);
        MyExpression.showExpression(mouth,true,R.drawable.layout);
        Timer timer = new Timer();
        timer.schedule(task,1000,10000);



    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyExpression.showExpression(leftEye,true,R.drawable.layout);
                    MyExpression.showExpression(rightEye,true,R.drawable.layout);
                    MyExpression.showExpression(mouth,true,R.drawable.layout);
                }
            });

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                serversSocket.startServer(new ServersSocket.ClientDataCallBack() {
                    @Override
                    public void getClientData(int connectMode, String str) {
                        Log.e("main",connectMode+"----------");
                        Log.e("main",str+"==========");

                    }
                });
                break;
            case R.id.btn_broadcast:
                final String ip = NetWorkUtils.getWifiAddress(context);
                UDPSocketBroadCast.getInstance().startUDP("111111", 8681);
                break;
        }
    }

    private BroadcastReceiver myBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String str = intent.getStringExtra("str");
            Log.e("main",str);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("updata");
        registerReceiver(myBroadcast,filter);

    }
}
