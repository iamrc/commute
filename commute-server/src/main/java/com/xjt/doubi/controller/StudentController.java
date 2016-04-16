package com.xjt.doubi.controller;

import com.xjt.doubi.bean.CheckState;
import com.xjt.doubi.bean.Message;
import com.xjt.doubi.bean.Student;
import com.xjt.doubi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午9:29
 * <p>
 * 处理 web端请求的控制器
 */
@Controller
@RequestMapping("/stu")
public class StudentController {

    @Autowired
    IStudentService studentService;


    /**
     * 获得所有学生的课堂状态
     *
     * @return List
     */
    @RequestMapping(value = "/getState.do", method = RequestMethod.GET)
    @ResponseBody
    public List<CheckState> getAllStudentState() {
        List<CheckState> list = studentService.getState();
        return list;
    }

    /**
     * 清空这一节上课情况，将考勤情况记录到历史记录
     * @return message
     */
    @RequestMapping(value = "/clearState.do",method = RequestMethod.GET)
    @ResponseBody
    public Message clearAllState(){
        int state = studentService.clearState();
        Message message = new Message();
        if(state == IStudentService.DELETE_STATE_SUCCESS){
            message.setState("ok");
            message.setState("清空成功");
        }else{
            message.setState("failed");
            message.setState("清空失败");
        }
        return message;
    }
}
