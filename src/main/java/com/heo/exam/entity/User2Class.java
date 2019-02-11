package com.heo.exam.entity;

import com.heo.exam.enums.UserTypeEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-01 13:43
 * @desc 学生对应班级对应表
 **/
@Entity(name = "user2class")
@Data
public class User2Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private int userType;

    private String classId;

    private Date createTime;

    private Date updateTime;

    public User2Class(){}

    public User2Class(String userId, UserTypeEnum userTypeEnum, String classId){
        this.userId = userId;
        this.userType = userTypeEnum.getTypeCode();
        this.classId = classId;
    }
}
