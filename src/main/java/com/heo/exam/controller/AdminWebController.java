package com.heo.exam.controller;

import com.heo.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 刘康
 * @Date 2019-05-26 0:25
 * @Description
 * @Revision
 **/
@Controller
public class AdminWebController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/upload")
    public String inputQuestionByExcel(@RequestParam MultipartFile file){
        new Thread(()-> questionService.excelInput("1",file)).start();
        return "success";
    }
}
