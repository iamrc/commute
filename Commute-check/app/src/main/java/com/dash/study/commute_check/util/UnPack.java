package com.dash.study.commute_check.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Dash
 * Date: 15/11/2 Time: 下午1:23
 */
public class UnPack {

    public static Map<String,String>getMessage(String str){
        Map<String,String> map = new HashMap<String,String>();
        JSONTokener jsonTokener = new JSONTokener(str);
        try {
            JSONObject message = (JSONObject) jsonTokener.nextValue();
            map.put("state",message.getString("state"));
            map.put("message",message.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
