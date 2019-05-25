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
        log.info("{}",resultVO.getData().getData());
        Assert.assertNotNull(resultVO.getData());
        Assert.assertNotEquals(resultVO.getData().getTotalData(),0);
        resultVO = paperService.getAllPaper("1", null,null,0,5);
        Assert.assertEquals(resultVO.getData().getTotalPages(),0);
        resultVO = paperService.getAllPaper(null,  1,null,0,5);
        Assert.assertEquals(resultVO.getData().getTotalPages(),1);
    }

    @Test
    @Transactional
    public void savePager() {
        ResultVO resultVO = paperService.savePaper(null, "1557924271452771376", 1, 10,"测试试卷", 100, "测试", new Integer[]{1,2});
        Assert.assertTrue(resultVO.getCode() == 0);
    }

    @Test
    public void deletePager() {
    }
}