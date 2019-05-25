package com.heo.exam.enums;

import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-02-19 14:12
 * @desc 题目类型
 **/
public enum  QuestionTypeEnum implements EnumCommon {

    CHOICE_QUESTION(0,"选择题"),
    JUDGE_QUESTION(1,"判断题"),
//    VACANCY_QUESTION(2,"填空题"),
    ;

    private Integer code;
    private String name;

    QuestionTypeEnum(Integer code,String name){
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
