package com.heo.exam.service.impl;

import com.heo.exam.entity.User;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.UploadService;
import com.heo.exam.service.UserService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import com.heo.exam.vo.UserInfoVO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author 刘康
 * @create 2019-01-31 16:53
 * @desc 为所有用户提供服务的实现
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadService uploadService;

    @Override
    public ResultVO getUserInfo(String userId) {
        User user = userRepository.findById(userId).get();
        return ResultVOUtil.success( new UserInfoVO(user));
    }

    @Transactional
    @Override
    public ResultVO updateUserInfo(String userId, UserInfoForm userInfoForm) {
        String avatar = userInfoForm.getAvatar();
        /**
         * 判断图片是微信的url，还是本地服务器的
         *  如果图片是以http开头，肯定不是网上的
         * */
        if (Strings.isNotEmpty(avatar) && !avatar.matches("^http.*")){
            uploadService.saveFile(avatar);
        }

        userRepository.updateUserInfo(userId, userInfoForm);
        return getUserInfo(userId);
    }

}
