package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

/**
 * @author 刘康
 * @create 2019-03-23 16:42
 * @desc 点赞表
 **/
@Entity
@Data
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String userId;

    private String likedUserId;

    private Integer num;

    private Date createTime;

    private Date updateTime;

    public Liked() {
    }

    public Liked(String userId, String likedUserId) {
        this.userId = userId;
        this.likedUserId = likedUserId;
        this.num = 0;
        this.createTime = new Date(new java.util.Date().getTime());
    }


    public void setNumAdd() {
        this.num++;
    }
}
