package com.dash.study.commute_check.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dash.study.commute_check.R;
import com.dash.study.commute_check.service.CheckService;


public class CheckActivity extends AppCompatActivity {
    private EditText ipEditText;
    private Button checkBtn;
    private TextView checkMessage;
    private String macAddr;
    private String ipAddress;

    class MyServerConnection implements ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CheckService.MyBinder proxy = (CheckService.MyBinder) service;
            //直到获得state值
            String state = proxy.getState();
            Log.i("dash",state);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ipEditText = (EditText) findViewById(R.id.checkIp);
        checkBtn = (Button) findViewById(R.id.checkButton);
        checkMessage = (TextView) findViewById(R.id.checkMessage);
        Intent intent = getIntent();
        macAddr = intent.getStringExtra("mac");
        String message = intent.getStringExtra("message");
        String ip = intent.getStringExtra("ip");
        if(ip != null){
            ipEditText.setText(ip);
            ipEditText.setEnabled(false);
        }
        if(message != null){
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
    }

    public void doCheck(View view) {
        Intent intent = new Intent();
        intent.putExtra("mac", getMacAddress());
        ipAddress = ipEditText.getText().toString().trim();
        intent.putExtra("ip", ipAddress);
//        intent.setAction("checkService");
        intent.setClass(this, CheckService.class);
        startService(intent);
        checkMessage.setText("打卡成功");
        ipEditText.setEnabled(false);
        checkBtn.setClickable(false);
//        bindService(intent,new MyServerConnection(), Service.BIND_AUTO_CREATE);
    }

    /**
     * 获得mac地址
     * @return String mac 地址
     */
    private String getMacAddress() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifiManager.getConnectionInfo();
        return info.getMacAddress();
    }
}
