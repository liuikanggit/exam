package com.heo.exam.enums;

import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-02-22 10:38
 * @desc excel录入的时候，错误枚举
 **/
@Getter
public enum ExcelInputEnum {

    SQL_ERROR(001,"sql执行错误"),
    QUESTION_EXIST(002,"题目重复"),

    SUBJECT_EMPTY(101,"科目为空"),
    SUBJECT_NO_EXIST(102,"科目不存在"),

    GRADE_EMPTY(201,"年级为空"),
    GRADE_NO_EXIST(202,"年级不存在"),

    QUESTION_TYPE_EMPTY(301,"题目类别为空"),
    QUESTION_TYPE_NO_EXIST(302,"题目类别不存在"),

    TITLE_EMPTY(302,"标题为空"),

    ANSWER_EMPTY(302,"标题为空"),

    ;

    private Integer code;

    private String message;

    ExcelInputEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
