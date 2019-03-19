package com.heo.exam.controller;

import com.heo.exam.service.WechatService;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘康
 * @create 2019-01-31 11:19
 * @desc 微信登录控制器
 **/
@RestController
@RequestMapping("/wx")
public class WechatLoginController {

    @Autowired
    private WechatService wechatLoginService;

    /**
     * 登录（第一次登录会自动注册）
     * @param type
     * @return
     */
    @PostMapping("/login")
    public ResultVO login(@RequestParam String code,@RequestParam Integer type,
                          @RequestParam(required = false) String[] formId,
                          @RequestParam String nickName,@RequestParam String avatarUrl,
                          @RequestParam String gender){
        return wechatLoginService.loginOrRegister(code,type,formId,nickName,avatarUrl,gender);
    }

}
