package com.xjt.doubi.controller;

import com.xjt.doubi.bean.Message;
import com.xjt.doubi.bean.Student;
import com.xjt.doubi.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * User: Dash
 * Date: 15/11/2 Time: 上午8:43
 * <p>
 * 处理android 端的请求的控制器
 */
@Controller
@RequestMapping("/ws/stu")
public class StudentWSController {

    @Autowired
    IStudentService studentService;

    /**
     * 学生信息注册
     * 成功与失败返回相应的信息
     *
     * @return Message 对象
     */
    @ResponseBody
    @RequestMapping(value = "/signUp.do", method = RequestMethod.POST)
    public Message signUp(@RequestBody Student student) {
        int state = studentService.singUp(student);
        Message message = new Message();
        if (state == IStudentService.STUDENT_IS_EXIST) {
            message.setState("failed");
            message.setMessage("学生已存在");
        } else if (state == IStudentService.STUDENT_IS_NOT_EXIST) {
            message.setState("ok");
            message.setMessage("注册成功");
        }
        return message;
    }

    /**
     * 学生打卡,利用请求的 MAC地址，进行相应
     *
     * @param mac mac地址
     * @return 打卡是否成功的信息
     */
    @ResponseBody
    @RequestMapping(value = "/check.do/{mac}")
    public Message check(@PathVariable("mac") String mac) {
        int state = studentService.check(mac);
        Message message = new Message();
        if (state == IStudentService.CHECK_FAILED) {
            message.setState("failed");
            message.setMessage("打卡失败");
        } else if (state == IStudentService.CHECK_SUCCESS) {
            message.setState("ok");
            message.setMessage("打卡成功");
        }
        return message;
    }
}
