package com.heo.exam.service;

import com.heo.exam.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-01-31 16:02
 * @desc 后台管理接口服务
 **/
public interface AdminService {

    ResultVO login(String username, String password);

    ResultVO logout();

}
