package com.heo.exam.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.exam.entity.User;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.utils.DateSerializer;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-01 10:03
 * @desc 返回给前端的用户信息类
 **/
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Data
public class UserInfoVO {

    private String name;

    private String avatar;

    private String job;

    private Integer sex;

    private String nid;

    private String phone;

    private String desc;

    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateSerializer.class)
    private Date updateTime;

    public UserInfoVO(User user){
        BeanUtils.copyProperties(user,this);
        this.job = UserTypeEnum.getTypeName(user.getType());
    }
}
