package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

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

}
