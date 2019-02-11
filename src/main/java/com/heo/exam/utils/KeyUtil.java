package com.heo.exam.utils;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author 刘康
 * @create 2019-01-31 10:44
 * @desc 生成唯一主键
 **/
public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式: 时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        return System.currentTimeMillis() + String.valueOf(getRandomNumber());
    }

    public static synchronized String getUUID(){
        return UUID.randomUUID().toString() + String.valueOf(getRandomNumber());
    }

    public static synchronized String getClassKey() {
        return String.valueOf(getRandomNumber());
    }

    /**
     * 生成6位随机数
     * @return
     */
    public static Integer getRandomNumber(){
        return new Random().nextInt(900000) + 100000;
    }

    public static void main(String[] args) {
        for (int i=0;i<10;i++) {
            System.out.println(genUniqueKey());
        }
    }
}

