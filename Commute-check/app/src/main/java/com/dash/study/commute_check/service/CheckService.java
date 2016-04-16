package com.dash.study.commute_check.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dash.study.commute_check.connection.Conn;
import com.dash.study.commute_check.util.UnPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午10:16
 */
public class CheckService extends Service {
    private String macAddress;
    private String ipAddress;
    private String state;

    /**
     * 网络链接然后打卡签到
     */
    class CheckRun implements Runnable {
        private int count = 0;
        private boolean isChecked = false;

        @Override
        public void run() {
            while (!isChecked) {
                try {
                    String url = "http://" + ipAddress + ":8080/ws/stu/check.do/" + macAddress;
                    HttpURLConnection connection = Conn.getConnection(url);
                    int code = connection.getResponseCode();
                    if (code == 200) {
                        InputStream is = connection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String response = br.readLine();
                        Log.i("dash", response);
                        //获得打卡是否成功的状态
                        //目前响应消息还无法接受
                        state = UnPack.getMessage(response).get("state");
                        ++count;
                        Log.i("dash", count + "");
                        if (count == 5) {
                            //确认上课，停止向服务器上传打卡请求
                            isChecked = true;
                        }
                    } else {
                        Log.i("dash", code + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    //每五秒打一次卡
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //关闭自己的服务
            stopSelf();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        macAddress = intent.getStringExtra("mac");
        ipAddress = intent.getStringExtra("ip");
//        开启一个新的线程进行网络连接
        new Thread(new CheckRun()).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        public String getState() {
            return state;
        }
    }
}
