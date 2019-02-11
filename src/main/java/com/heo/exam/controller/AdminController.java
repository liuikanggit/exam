package com.heo.exam.controller;

import com.heo.exam.service.AdminService;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘康
 * @create 2019-01-31 15:56
 * @desc 后台管理控制器
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResultVO login(@RequestParam String username,@RequestParam String password){
        return adminService.login(username,password);
    }

    @PostMapping("/logout")
    public ResultVO logout(){
        return adminService.logout();
    }

}
