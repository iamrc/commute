package com.xjt.doubi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午8:39
 */
public class Main {
    public static void main(String[] args){
        try {
            String name = URLDecoder.decode("%E6%B2%88%E8%BE%BE","utf-8");
            System.out.println(name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
