package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-03 18:55
 * @desc 考试信息
 **/
@Entity
@Data
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer examId;

    private String userId;

    private Date startTime;

    private Date submitTime;

    private Date endTime;

    private BigDecimal score;

    public Exam(){}

    public Exam(Integer examId, String userId) {
        this.examId = examId;
        this.userId = userId;
        this.score = new BigDecimal(0);
    }
}
