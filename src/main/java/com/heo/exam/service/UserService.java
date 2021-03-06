package com.heo.exam.service;

import com.heo.exam.form.UserInfoForm;
import com.heo.exam.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-01-31 16:51
 * @desc 用户信息相关的服务
 **/
public interface UserService {

    ResultVO getUserInfo(String userId);

    ResultVO updateUserInfo(String userId,UserInfoForm userInfoForm);

    ResultVO like(String userId,String likedUserId);

}
