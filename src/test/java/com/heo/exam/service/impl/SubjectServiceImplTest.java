package com.heo.exam.service.impl;

import com.heo.exam.ExamApplicationTests;
import com.heo.exam.entity.Subject;
import com.heo.exam.repository.SubjectRepository;
import com.heo.exam.service.SubjectService;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class SubjectServiceImplTest extends ExamApplicationTests {


    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void getAllSubject() {
        ResultVO result = subjectService.getAllSubject();
        Assert.assertEquals(result.getCode(),Integer.valueOf(0));
        log.info("subject:{}",result.getData());
    }

    @Test
    @Transactional
    public void createSubject(){
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(new Subject("语文"));
        subjectList.add(new Subject("数学"));
        subjectList.add(new Subject("外语"));
        subjectList.add(new Subject("生物"));
        subjectList.add(new Subject("地理"));
        subjectList.add(new Subject("物理"));
        subjectList.add(new Subject("化学"));
        subjectList.add(new Subject("计算机"));
        subjectRepository.saveAll(subjectList);
    }
}