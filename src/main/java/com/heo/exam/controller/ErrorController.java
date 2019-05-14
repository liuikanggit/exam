package com.heo.exam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 刘康
 * @create 2019-04-02 20:10
 * @desc 没啥子用的控制器
 **/
@Controller
public class ErrorController {

    @RequestMapping("error-404")
    public String error404(){
        return "errorPage/404";
    }
}
