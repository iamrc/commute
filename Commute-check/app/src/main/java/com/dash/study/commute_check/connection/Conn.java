package com.dash.study.commute_check.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午11:04
 * <p/>
 * 用于获得服务器连接对象
 */
public class Conn {

    private static HttpURLConnection connection;

    private Conn() {
    }

    public static HttpURLConnection getConnection(String path) {
            try {
                URL url = new URL(path);
                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestProperty("Accept-Charset", "UTF-8");
//                connection.setRequestProperty("contentType", "UTF-8");
                //设置连接超时时间
//                connection.setConnectTimeout(5000);
                //允许输出输入
                connection.setDoOutput(true);
                connection.setDoInput(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return connection;
    }
}
