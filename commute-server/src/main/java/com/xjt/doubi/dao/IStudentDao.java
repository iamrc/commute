package com.xjt.doubi.dao;

import com.xjt.doubi.bean.Student;

public interface IStudentDao {
    int deleteByPrimaryKey(String id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(String id);
    Student selectByMac(String mac);
    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}