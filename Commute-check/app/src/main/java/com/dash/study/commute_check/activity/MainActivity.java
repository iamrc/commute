package com.dash.study.commute_check.activity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dash.study.commute_check.R;

public class MainActivity extends AppCompatActivity {
    private EditText ipAddrEt;

    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activiyty);
        ipAddrEt = (EditText) findViewById(R.id.ipAddrEt);
    }

    /**获取数据
     *
     */
    private void fetch() {
        String ipAddr = ipAddrEt.getText().toString().trim();
        if(validate(ipAddr)){
            ip = ipAddr;
        }else{
            ipAddrEt.setText("");
        }
    }

    /**
     * 验证字符串
     * @param ipAddr
     * @return
     */
    private boolean validate(String ipAddr) {
        if(ipAddr.equals("")){
            Toast.makeText(this,"ip 地址不能为空",Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }
    }

    public void check(View view) {
        fetch();
        Intent intent = new Intent();
//        intent.setAction("check");
        intent.putExtra("mac", getMacAddress());
        intent.putExtra("ip", ip);
        intent.setClass(this, CheckActivity.class);
        startActivity(intent);
    }

    public void signUp(View view) {
        Intent intent = new Intent();
//        intent.setAction("signUp");
        intent.putExtra("mac", getMacAddress());
        intent.setClass(this,SignUpActivity.class);
        startActivity(intent);
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

    /**
     * 退出APP
     * @param view
     */
    public void cancel(View view) {
       this.finish();
    }
}
