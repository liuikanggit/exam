package com.heo.exam.service.impl;

import com.heo.exam.service.PaperService;
import com.heo.exam.vo.PageVo;
import com.heo.exam.vo.PaperSimpleVO;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class PaperServiceImplTest {

    @Autowired
    private PaperService paperService;

    @Test
    public void getAllPager() {
        ResultVO<PageVo<PaperSimpleVO>> resultVO = paperService.getAllPaper(null, null,null,0,5);
        Assert.assertNotNull(resultVO.getData());
        Assert.assertNotEquals(resultVO.getData().getTotalData(),0);
        resultVO = paperService.getAllPaper("1", null,null,0,5);
        Assert.assertEquals(resultVO.getData().getTotalPages(),0);
        resultVO = paperService.getAllPaper(null,  49,null,0,5);
        Assert.assertEquals(resultVO.getData().getTotalPages(),1);
    }

    @Test
    public void savePager() {
        ResultVO resultVO = paperService.savePaper(3, "1552562802448169525", 48, 10,"测试试卷", 100, "测试", new Integer[]{1,2,3});
        log.info("{}",resultVO.getCode());
        log.info("{}",resultVO.getMsg());
        log.info("{}",resultVO.getData());
    }

    @Test
    public void deletePager() {
    }
}