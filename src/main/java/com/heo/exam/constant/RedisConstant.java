package com.heo.exam.constant;

/**
 * @author 刘康
 * @create 2019-01-31 13:38
 * @desc  redis 与 token 相关的配置
 **/
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 604800; //7天

    // header中保存token的字段
    String AUTHORIZATION = "Authorization";

    String FORM_ID_PREFIX = "formId_%s";


    String ACCESS_TOKEN = "access_token";
}
