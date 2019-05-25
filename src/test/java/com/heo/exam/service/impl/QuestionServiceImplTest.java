package com.heo.exam.service.impl;

import com.heo.exam.ExamApplicationTests;
import com.heo.exam.service.QuestionService;
import com.heo.exam.vo.ResultVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

public class QuestionServiceImplTest extends ExamApplicationTests {

    @Autowired
    private QuestionService questionService;

    @Test
    @Transactional
    public void inputChoiceQuestion() {
        ResultVO  resultVO =  questionService.inputChoiceQuestion(1,"1557924271452771376",1,"1+1=","2","3","4","1","众所周知，在地球上1+1=2");
        Assert.assertTrue(resultVO.getCode() == 0);
    }

    @Test
    @Transactional
    public void inputJudgementQuestion() {
        ResultVO  resultVO =  questionService.inputJudgementQuestion(1,"1557924271452771376",1,"1-1=0",true,"众所周知，在的地球上1-1=0");
        Assert.assertTrue(resultVO.getCode() == 0);
    }
}