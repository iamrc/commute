package com.xjt.doubi.dao;

import com.xjt.doubi.bean.CheckState;

import java.util.List;

public interface ICheckStateDao {
    int deleteByPrimaryKey(String id);
    int deleteAllState();
    int insert(CheckState record);

    int insertSelective(CheckState record);

    CheckState selectByPrimaryKey(String id);
    CheckState selectByMac(String mac);
    List<CheckState> selectAllState();


    int updateByPrimaryKeySelective(CheckState record);
    int updateByPrimaryKey(CheckState record);
}