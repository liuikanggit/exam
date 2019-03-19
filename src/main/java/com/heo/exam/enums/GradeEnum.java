package com.heo.exam.enums;

import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-02-12 17:27
 * @desc 年级枚举
 **/
public enum GradeEnum implements EnumCommon {

    UNCLASSIFIED(0, ""),
    FIRST_GRADE(1, "一年级"),
    SECOND_GRADE(2, "二年级"),
    THIRD_GRADE(3, "三年级"),
    FOURTH_GRADE(4, "四年级"),
    FIFTH_GRADE(5, "五年级"),
    SIXTH_GRADE(6, "六年级"),
    SEVENTH_GRADE(7, "初一"),
    EIGHTH_GRADE(8, "初二"),
    NINTH_GRADE(9, "初三"),
    TENTH_GRADE(10, "高一"),
    ELEVENTH_GRADE(11, "高二"),
    TWELFTH_GRADE(12, "高三"),
    THIRTEENTH_GRADE(13, "大一"),
    FOURTEENTH_GRADE(14, "大二"),
    FIFTEENTH_GRADE(15, "大三"),
    SIXTEENTH_GRADE(16, "大四"),

    ;
    private Integer code;
    private String name;

    GradeEnum(Integer code, String name) {
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
