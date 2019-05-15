package com.heo.exam.service.impl;

import com.heo.exam.entity.User;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.AdminService;
import com.heo.exam.service.RedisService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 刘康
 * @create 2019-01-31 16:05
 * @desc 后台管理接口服务实现类
 **/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public ResultVO login(String username, String password) {
        Integer type = UserTypeEnum.ADMIN.getCode();
        User admin = userRepository.findByUsernameAndType(username,type);
        if(admin == null){
            throw new ExamException(ResultEnum.ADMIN_EMPTY);
        }

        if(password == null || password.equals(admin.getPassword())){
            throw new ExamException(ResultEnum.ADMIN_PASSWORD_ERROR);
        }

       String token =  redisService.login(admin.getId());

        return ResultVOUtil.success(token);
    }

    @Override
    public ResultVO logout() {
        return ResultVOUtil.success();
    }

}
