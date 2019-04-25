package com.heo.exam.service.impl;

import com.heo.exam.service.ClassService;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClassServiceImplTest {

    @Autowired
    private ClassService classService;

    @Test
    public void getClassInfo() {
    }

    @Test
    public void getClassAndUserList() {
        ResultVO classAndUserList = classService.getClassAndUserList("1552562541387320243", "111111");
        log.info("{}",classAndUserList);
    }

    @Test
    @Transactional
    public void dissolutionClass(){
        ResultVO resultVO = classService.disbandClass("1","000000");
        log.info("{}",resultVO);
    }

    @Test
    public void joinClass() {
    }

    @Test
    public void quitClass() {
    }

    @Test
    public void createClass() {
    }

    @Test
    public void getAllJoinedClassInfo() {
    }
}