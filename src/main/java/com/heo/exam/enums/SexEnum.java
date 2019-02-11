package com.heo.exam.enums;


import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-01-31 13:47
 * @desc 性别枚举
 **/
@Getter
public enum  SexEnum {

    UNKNOWN(-1,"未填写"),
    MALE(0,"男"),
    FEMALE(1,"女");

    private Integer code;

    private String text;

    SexEnum(Integer code,String text){
        this.code = code;
        this.text = text;
    }
}
