package com.heo.exam.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-24 20:50
 * @desc
 **/
@Data
public class ExamQuestionVo {

    private Integer id;

    private String name;

    private Long time;

    private List<QuestionVO> questions;
}
