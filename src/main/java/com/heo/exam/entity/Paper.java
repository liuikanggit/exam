package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-03 18:46
 * @desc 试卷
 **/
@Data
@Entity
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String creatorId;

    private Integer subjectId;

    private Integer gradeCode;

    private String name;

    private Integer score;

    @Column(name = "paper_desc")
    private String desc;

    private Date createTime;

    private Date updateTime;

    public Paper() {
    }

    public Paper(String creatorId, Integer subjectId,Integer gradeCode, String name, Integer score, String desc) {
        this.name = name;
        this.creatorId = creatorId;
        this.subjectId = subjectId;
        this.gradeCode = gradeCode;
        this.score = score;
        this.desc = desc;
    }
}
