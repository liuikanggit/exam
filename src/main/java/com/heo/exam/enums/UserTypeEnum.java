package com.heo.exam.enums;

import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-01-31 13:38
 * @desc 用户类型枚举
 **/
@Getter
public enum UserTypeEnum {

    STUDENT(0,"学生"),
    TEACHER(1,"教师"),
    ADMIN(2,"系统管理员");

    private Integer typeCode;

    private String typeName;

    UserTypeEnum(Integer typeCode,String typeName){
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public static boolean contains(Integer typeCode){
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if(userTypeEnum.getTypeCode().equals(typeCode)){
                return true;
            }
        }
        return false;
    }

    public static String getTypeName(Integer typeCode){
        for (UserTypeEnum userTypeEnum : UserTypeEnum.values()) {
            if(userTypeEnum.getTypeCode().equals(typeCode)){
                return userTypeEnum.getTypeName();
            }
        }
        return "";
    }

}
