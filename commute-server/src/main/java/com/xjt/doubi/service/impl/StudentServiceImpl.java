package com.xjt.doubi.service.impl;

import com.xjt.doubi.bean.CheckState;
import com.xjt.doubi.bean.Student;
import com.xjt.doubi.dao.ICheckStateDao;
import com.xjt.doubi.dao.IStudentDao;
import com.xjt.doubi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午9:11
 * <p>
 * student 业务层
 */
@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private ICheckStateDao checkStateDao;

    @Autowired
    private IStudentDao studentDao;

    /**
     * 学生信息注册
     * 根据学生返回已经存在，返回相应的字符串状态信息
     *
     * @param student 待注册的学生
     * @return int
     */
    public int singUp(Student student) {
        Student stu = studentDao.selectByMac(student.getMac());
        if (stu == null) {
            stu = student;
            studentDao.insert(stu);
            return STUDENT_IS_NOT_EXIST;
        } else {
            return STUDENT_IS_EXIST;
        }
    }

    /**
     * 学生打卡
     * 利用请求的 MAC地址，给相应的 MAC地址的学生,记录
     * 一次签到信息,返回相应的签到响应消息
     *
     * @param mac 请求的 MAC 地址
     * @return int 响应消息
     */
    public int check(String mac) {
        Student student = studentDao.selectByMac(mac);
        int status;
        CheckState checkState = checkStateDao.selectByMac(mac);
        if (checkState == null) {
            checkState = new CheckState();
            checkState.setId(student.getId());
            checkState.setName(student.getName());
            checkState.setMac(mac);
            checkState.setState(0);
            status = checkStateDao.insert(checkState);
        } else {
            //状态+1
            int state = checkState.getState();
            if (state == 5) {
                return 0;
            }
            ++state;
            checkState.setState(state);
            status = checkStateDao.updateByPrimaryKey(checkState);
        }
        return status;
    }

    /**
     * 获得所有学生的课堂状态
     *
     * @return List
     */
    public List<CheckState> getState() {
        //通过调用 dao 层获得所有学生的状态
        List<CheckState> list = checkStateDao.selectAllState();
        return list;
    }


    /**
     * 清空一节所有学生的状态
     */
    public int clearState() {
        List<CheckState> list = checkStateDao.selectAllState();
        Iterator<CheckState> iterator = list.iterator();
        while (iterator.hasNext()) {
            CheckState temp = iterator.next();
            System.out.println(temp.getId());
            checkStateDao.deleteByPrimaryKey(temp.getId());
        }
        return DELETE_STATE_SUCCESS;
    }
}
