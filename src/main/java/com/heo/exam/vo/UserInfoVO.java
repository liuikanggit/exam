package com.heo.exam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.exam.entity.User;
import com.heo.exam.enums.SexEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.utils.DateSerializer;
import com.heo.exam.utils.EnumUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-01 10:03
 * @desc 返回给前端的用户信息类
 **/
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Data
public class UserInfoVO {

    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 职位
     */
    private String job;

    private Integer likedNum;

    /** 今天是否有人给点过赞 */
    private boolean isLiked;

    /**
     * 性别
     */
    private Integer sex;

    private String nid;

    private String phone;

    private String desc;

    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateSerializer.class)
    private Date updateTime;

    public UserInfoVO(User user) {
        BeanUtils.copyProperties(user, this);
        this.job = EnumUtil.getName(UserTypeEnum.class, user.getType());
    }

    public static List<UserInfoVO> getUserInfoVOList(List<User> userList) {
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        userList.forEach(user ->
                userInfoVOList.add(new UserInfoVO(user))
        );
        return userInfoVOList;
    }
}
