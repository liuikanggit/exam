package com.heo.exam.entity;

import com.heo.exam.enums.QuestionTypeEnum;
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

    public Question(){}

    public Question(Integer subjectId, String creatorId, int grade, String title, String answer0, String answer1, String answer2, String answer3, String analysis) {
        this.subjectId = subjectId;
        this.creatorId = creatorId;
        this.grade = grade;
        this.title = title;
        this.type = QuestionTypeEnum.CHOICE_QUESTION.getCode();
        this.answer0 = answer0;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.analysis = analysis;
    }

    public Question(Integer subjectId, String creatorId, int grade, String title, boolean answer, String analysis) {
        this.subjectId = subjectId;
        this.creatorId = creatorId;
        this.grade = grade;
        this.type = QuestionTypeEnum.JUDGE_QUESTION.getCode();
        this.title = title;
        this.answer0 = answer ? "对" : "错";
        this.analysis = analysis;
    }
}
