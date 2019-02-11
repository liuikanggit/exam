package com.heo.exam.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-01 11:02
 * @desc 班级类
 **/
@Entity(name = "class")
@Data
public class Clazz {

    @Id
    private String id;

    private String createId;

    private String name;

    private String avatar = "";

    private String password = "";

    private String grade = "";

    @Column(name = "class_desc")
    private String desc;

    private Date createTime;

    private Date updateTime;

    public Clazz(){
    }

}
