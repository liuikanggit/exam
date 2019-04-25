package com.heo.exam.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 刘康
 * @create 2019-04-03 18:51
 * @desc 试卷和题目对应表
 **/
@Entity
@Data
@Table(name = "paper2question")
public class Paper2Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer paperId;

    private Integer questionId;

    public Paper2Question() {
    }

    public Paper2Question(Integer paperId, Integer questionId) {
        this.paperId = paperId;
        this.questionId = questionId;
    }

}
