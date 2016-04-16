package com.dash.study.commute_check.bean;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午11:30
 */
public class Student {
    private String name;
    private String id;
    private String cla;
    private String gender;
    private String mac;
    private String pic;
    private int age;

    public Student(String name, String gender, String id, int age, String cla, String mac) {
        this.name = name;
        this.gender = gender;
        this.id = id;
        this.age = age;
        this.cla = cla;
        this.mac = mac;
    }

    public Student() {

    }

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
