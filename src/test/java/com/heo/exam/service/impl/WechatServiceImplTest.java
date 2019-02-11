package com.heo.exam.service.impl;

import com.heo.exam.service.WechatService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WechatServiceImplTest {

    @Autowired
    private WechatService wechatService;

    @Test
    public void loginOrRegister() {
        wechatService.loginOrRegister("1",1);
    }
}