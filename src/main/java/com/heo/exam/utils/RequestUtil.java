package com.heo.exam.utils;

import com.heo.exam.constant.RedisConstant;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 刘康
 * @create 2019-01-31 18:04
 * @desc request工具类
 **/
public class RequestUtil {

    public static String getToekn(){
        /** 从请求头中获取token */
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =  attributes.getRequest();
        // 查询token
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        return token;
    }



}
