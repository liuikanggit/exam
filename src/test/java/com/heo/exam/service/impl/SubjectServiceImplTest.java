package com.heo.exam.service.impl;

import com.heo.exam.service.SubjectService;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SubjectServiceImplTest {


    @Autowired
    private SubjectService subjectService;

    @Test
    public void getAllSubject() {
        ResultVO result = subjectService.getAllSubject();
        Assert.assertEquals(result.getCode(),Integer.valueOf(0));
        log.info("subject:{}",result.getData());
    }
}