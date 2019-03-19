package com.heo.exam.utils;


import com.heo.exam.enums.EnumCommon;
import com.heo.exam.enums.GradeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-21 20:16
 * @desc 枚举工具类
 **/
public class EnumUtil {

    private static <T> boolean isEnumCommon(Class<T> enumT) {
        if (!enumT.isEnum()) {
            return false;
        }
        Class<?>[] interfaces = enumT.getInterfaces();
        boolean f = false;
        for (Class i : interfaces) {
            if (i.equals(EnumCommon.class)) {
                f = true;
                break;
            }
        }
        if (!f) {
            return false;
        }
        return true;
    }

    public static <T> List<String> getNameList(Class<T> enumT) {
        List<String> list = new ArrayList<>();
        if (!isEnumCommon(enumT)) {
            return list;
        }
        for (T en : enumT.getEnumConstants()) {
            EnumCommon enumCommon = (EnumCommon) en;
            list.add(enumCommon.getName());
        }
        return list;
    }

    public static <T> boolean contains(Class<T> enumT,String name) {
        if (!isEnumCommon(enumT)) {
            return false;
        }
        for (T en : enumT.getEnumConstants()) {
            EnumCommon enumCommon = (EnumCommon) en;
            if (enumCommon.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public static <T> boolean contains(Class<T> enumT,Integer code) {
        if (!isEnumCommon(enumT)) {
            return false;
        }
        for (T en : enumT.getEnumConstants()) {
            EnumCommon enumCommon = (EnumCommon) en;
            if (enumCommon.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }

    public static <T> Integer getCode(Class<T> enumT,String name,Integer defaultValue){
        if (!isEnumCommon(enumT)) {
            return defaultValue;
        }
        for (T en : enumT.getEnumConstants()) {
            EnumCommon enumCommon = (EnumCommon) en;
            if (enumCommon.getName().equals(name)){
                return enumCommon.getCode();
            }
        }
        return defaultValue;
    }
    public static <T> Integer getCode(Class<T> enumT,String name){
        return getCode(enumT,name,-1);
    }

    public static <T> String getName(Class<T> enumT,Integer code,String defaultValue){
        if (!isEnumCommon(enumT)) {
            return defaultValue;
        }
        for (T en : enumT.getEnumConstants()) {
            EnumCommon enumCommon = (EnumCommon) en;
            if (enumCommon.getCode().equals(code)){
                return enumCommon.getName();
            }
        }
        return defaultValue;
    }
    public static <T> String getName(Class<T> enumT,Integer code){
        return getName(enumT,code,"");

    }

    public static void main(String[] args) {
        System.out.println(EnumUtil.getNameList(GradeEnum.class));
        System.out.println(EnumUtil.contains(GradeEnum.class,6));
    }
}
