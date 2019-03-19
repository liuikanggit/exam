package com.heo.exam.enums;

import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-01-31 13:38
 * @desc 用户类型枚举
 **/
public enum UserTypeEnum implements EnumCommon {

    STUDENT(0,"学生"),
    TEACHER(1,"教师"),
    ADMIN(2,"系统管理员"),
    ;

    private Integer code;

    private String name;

    UserTypeEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }}
