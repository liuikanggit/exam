package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 刘康
 * @create 2019-02-20 22:00
 * @desc 学科
 **/
@Entity
@Data
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String icon;

    private Date createTime;

    private Date updateTime;

    public Subject(){}

    public Subject(String name){
        this.name = name;
    }

    public Subject(String name,String icon){
        this.name = name;
        this.icon = icon;
    }

    public Map<String,Object> toMap(){
        Map<String ,Object> map = new LinkedHashMap<>();
        map.put("name",this.name);
        map.put("value",this.id);
        return map;
    }

}
