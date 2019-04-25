package com.heo.exam.controller;

import com.heo.exam.service.ClassService;
import com.heo.exam.service.RedisService;
import com.heo.exam.service.UserService;
import com.heo.exam.service.impl.ClassServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * @author 刘康
 * @create 2019-01-31 16:48
 * @desc 控制器的基类，提供获取userId的方法
 **/
public class  BaseController {

    @Autowired
    protected RedisService redisService;

    @Autowired
    protected UserService userService;

    protected String getUserId(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        String userId = (String) request.getAttribute("userId");

        ClassService classService = new ClassServiceImpl();

        return userId;
    }

}
