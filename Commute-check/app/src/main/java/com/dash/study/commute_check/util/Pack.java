package com.dash.study.commute_check.util;

import com.dash.study.commute_check.bean.Student;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午11:01
 *
 * 用于数据打包类
 */
public class Pack {
//    Student student = new Student(uname, gender, id, age, cla, macAddr);
    public static String getJson(Student student){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",student.getName());
            jsonObject.put("mac",student.getMac());
            jsonObject.put("id",student.getId());
            jsonObject.put("age",student.getAge());
            jsonObject.put("gender",student.getGender());
            jsonObject.put("cla", student.getCla());
            return String.valueOf(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String passwd){
       //md5+sha1
        return null;
    }

}
