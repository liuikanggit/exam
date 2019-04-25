package com.heo.exam.vo;

import lombok.Data;

/**
 * @author 刘康
 * @create 2019-03-28 10:16
 * @desc 用户简单信息
 **/
@Data
public class UserSimpleInfo {

    private String id;

    private String name;

    private String avatar;

    private String desc;

    public UserSimpleInfo(String id,String name,String avatar,String desc){
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.desc  = desc;
    }
}
