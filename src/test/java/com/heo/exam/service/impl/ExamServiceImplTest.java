package com.heo.exam.service.impl;

import com.heo.exam.entity.ExamDetail;
import com.heo.exam.entity.Paper;
import com.heo.exam.service.ExamService;
import com.heo.exam.utils.DateUtil;
import com.heo.exam.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.*;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ExamServiceImplTest {

    @Autowired
    private ExamService examService;

    @Test
    @Transactional
    public void createExam() {
        String teacherId = "1553136628437305747";
        Integer paperId = 4;
        String[] classIds = new String[]{"123456"};
        String name = "第二次数学月考";
        String desc = "主要为了考察同学们最近的学习情况，同学们要加油哦..";
        Integer time = 120;

        /**  创建二个进行中的考试 */
        Calendar instance = Calendar.getInstance();
        Date startDate = instance.getTime();
        instance.add(Calendar.DATE,1);
        Date endDate =instance.getTime();

        ResultVO result = examService.createExam(teacherId, paperId, classIds,name+1,desc, startDate, endDate, time);
        Assert.assertEquals(result.getCode(),new Integer(0));
        result = examService.createExam(teacherId, paperId, classIds,name+2,desc, startDate, endDate, time);
        Assert.assertEquals(result.getCode(),new Integer(0));


        /**  创建一个5个小时后才开始的的考试 */
        instance = Calendar.getInstance();
        instance.add(Calendar.HOUR_OF_DAY,5);
        startDate = instance.getTime();
        instance.add(Calendar.DATE,1);
        endDate =instance.getTime();
        examService.createExam(teacherId, paperId, classIds,name+3,desc, startDate, endDate, time);
        Assert.assertEquals(result.getCode(),new Integer(0));
    }

    @Test
    public void getExamSimpleInfoByStudent() {
        ResultVO<PageVo<ExamSimpleInfoVO>> resultVO = examService.getExamSimpleInfoByStudent("1553578680507837713", 0, 5);
        Assert.assertEquals(resultVO.getCode(),new Integer(0));
        log.info("{}",resultVO.getData());
    }

    @Test
    public void getExamSimpleInfoByTeacher() {
        ResultVO<ExamDetail> result = examService.getExamDetailInfoByStudent("1553136628437305747",10);
        Assert.assertEquals(result.getCode(),new Integer(0));
        log.info("{}",result.getData());
}

    @Test
    public void getExamDetailInfoByStudent() {
    }

    @Test
    public void getExamDetailInfoByTeacher() {

        ResultVO<PageVo<List<TExamSimpleInfoVO>>> result = examService.getExamSimpleInfoByTeacher("1553136628437305747",0,5);
        Assert.assertEquals(result.getCode(),new Integer(0));
        log.info("{}",result.getData().getData());
    }

    @Test
    public void startExam() {
        ResultVO<List<QuestionVO>> result = examService.startExam("1553136628437305747",12);
        Assert.assertEquals(result.getCode(),new Integer(0));
        log.info("{}",result.getData());
    }

    @Test
    public void saveExam() {
    }

    @Test
    public void submitExam() {
    }

    @Test
    public void getEndExamSimpleInfoByStudent(){
        ResultVO result = examService.getEndExamSimpleInfoByStudent("1553578680507837713",0,10);
        Assert.assertEquals(result.getCode(),new Integer(0));
        log.info("{}",result.getData());
    }
}