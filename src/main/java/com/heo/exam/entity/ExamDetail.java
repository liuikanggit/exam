package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author 刘康
 * @create 2019-04-09 16:39
 * @desc 考试详细信息
 **/
@Data
@Entity
public class ExamDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer examId;

    private String userId;

    private Integer questionId;

    private String answer;

    public ExamDetail(){}

    public ExamDetail(Integer examId, String userId, Integer questionId, String answer) {
        this.examId = examId;
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
    }
}
