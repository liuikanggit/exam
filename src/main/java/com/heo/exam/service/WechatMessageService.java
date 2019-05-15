package com.heo.exam.service;

/**
 * 微信注册成功模板
 */
public interface WechatMessageService {

    boolean sendRegisterNotice(String openid, String name, String userType);
}
