package com.xjt.doubi.service;

import com.xjt.doubi.bean.CheckState;
import com.xjt.doubi.bean.Student;

import java.util.List;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午9:10
 */
public interface IStudentService {
    public static final int STUDENT_IS_EXIST=1;
    public static final int STUDENT_IS_NOT_EXIST=2;
    public static final int CHECK_SUCCESS=3;
    public static final int CHECK_FAILED=4;
    public static final int DELETE_STATE_SUCCESS=5;
    public static final int DELETE_STATE_FAILED=6;

    public int singUp(Student student);
    public int check(String mac);
    public List<CheckState> getState();
    public int clearState();
}
