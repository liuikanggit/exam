package com.heo.exam.enums;


/**
 * @author 刘康
 * @create 2019-01-31 13:47
 * @desc 性别枚举
 **/
public enum  SexEnum implements EnumCommon {

    UNKNOWN(0,"未填写"),
    MALE(1,"男"),
    FEMALE(2,"女");

    private Integer code;

    private String name;

    SexEnum(Integer code,String name){
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
