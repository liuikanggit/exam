package com.heo.exam.controller;

import com.heo.exam.service.AdminService;
import com.heo.exam.service.QuestionService;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

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

    @Autowired
    private QuestionService questionService;

    @PostMapping("/login")
    public ResultVO login(@RequestParam String username,@RequestParam String password){
        return adminService.login(username,password);
    }

    @PostMapping("/logout")
    public ResultVO logout(){
        return adminService.logout();
    }

    @GetMapping("/question/excel")
    public ResponseEntity<FileSystemResource> getExcelTemplate(){
        File file = questionService.getExcelTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }

    @PostMapping("/question/excel")
    public ResultVO inputQuestionByExcel(@RequestParam MultipartFile file){
        return questionService.excelInput("1",file);
    }

}
