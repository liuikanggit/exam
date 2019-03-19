package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-20 21:47
 * @desc 题目表
 **/
@Entity
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer subjectId;

    private String creatorId;

    private Integer grade;

    private Integer type;

    private String title;

    private String titleImage;

    @Column(name = "answer_0")
    private String answer0;

    @Column(name = "answer_image_0")
    private String answerImage0;

    @Column(name = "answer_1")
    private String answer1;

    @Column(name = "answer_image_1")
    private String answerImage1;

    @Column(name = "answer_2")
    private String answer2;

    @Column(name = "answer_image_2")
    private String answerImage2;

    @Column(name = "answer_3")
    private String answer3;

    @Column(name = "answer_image_3")
    private String answerImage3;

    private String analysis;

    private Date createTime;

    private Date updateTime;
}
