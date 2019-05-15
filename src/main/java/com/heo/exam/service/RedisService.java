package com.heo.exam.service;

/**
 * @author 刘康
 * @create 2019-01-31 15:51
 * @desc 做登录处理的service
 **/
public interface RedisService{

    String login(String userId);

    void logout(String userId);

    boolean verify();

    /**
     * 保存formId
     * @param userId
     * @param formId
     */
    void saveFormId(String userId,String formId);

    /**
     * 获取formId
     * @param openId
     * @return
     */
    String getFormId(String openId);

    /**
     * 获取accessToken
     * @return
     */
    String getAccessToken();

}
