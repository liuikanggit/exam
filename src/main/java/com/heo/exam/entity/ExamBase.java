package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-03 18:52
 * @desc 考试基本表
 **/
@Entity
@Data
public class ExamBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer paperId;

    private String classId;

    private String creatorId;

    private String name;

    @Column(name = "exam_desc")
    private String desc;

    private String subject;

    private Date beginTime;

    private Date endTime;

    private Integer time;

    private Date createTime;

    private Date updateTime;

    public ExamBase(){}

    public ExamBase(Integer paperId, String classId, String creatorId, String name, String desc,String subject, Date beginTime, Date endTime, Integer time) {
        this.paperId = paperId;
        this.classId = classId;
        this.creatorId = creatorId;
        this.name = name;
        this.desc = desc;
        this.subject = subject;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.time = time;
    }
}
