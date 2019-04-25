package com.heo.exam.service.impl;

import com.heo.exam.entity.Liked;
import com.heo.exam.entity.User;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.repository.LikedRepository;
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
import java.util.Date;
import java.util.Optional;

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

    @Autowired
    private LikedRepository likedRepository;

    @Override
    public ResultVO getUserInfo(String userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new ExamException(ResultEnum.USER_EMPTY);
        }
        user.setLikedNum(likedRepository.getLikeNumByLikedUserId(userId));
        UserInfoVO userInfoVO = new UserInfoVO(user);
        userInfoVO.setLiked(likedRepository.existsByLikedUserIdAndCreateTime(userId, new Date()));
        return ResultVOUtil.success(userInfoVO);
    }

    @Transactional
    @Override
    public ResultVO updateUserInfo(String userId, UserInfoForm userInfoForm) {
        String avatar = userInfoForm.getAvatar();
        uploadService.saveFile(avatar);
        userRepository.updateUserInfo(userId, userInfoForm);
        return getUserInfo(userId);
    }

    @Override
    public ResultVO like(String userId, String likedUserId) {

        Liked liked = likedRepository.findTodayUserSupport(userId, likedUserId);
        if (liked == null) {
            liked = new Liked(userId, likedUserId);
        }

        if (userId.equals(likedUserId)) {
            if (liked.getNum() >= 5) {
                throw new ExamException(ResultEnum.LIKE_EXCEED_5);
            }
        } else {
            if (liked.getNum() >= 10) {
                throw new ExamException(ResultEnum.LIKE_EXCEED_10);
            }
        }
        liked.setNumAdd();
        likedRepository.save(liked);
        return ResultVOUtil.success(likedRepository.getLikeNumByLikedUserId(likedUserId));
    }

}
