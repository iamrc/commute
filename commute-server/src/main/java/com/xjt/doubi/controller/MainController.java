package com.xjt.doubi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: Dash
 * Date: 15/11/2 Time: 下午11:42
 *
 * / 的controller
 */
@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(method = RequestMethod.GET)
    public String showIndex(){
       return "index";
    }
}
