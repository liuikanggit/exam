package com.heo.exam.controller;

import com.heo.exam.constant.FileType;
import com.heo.exam.service.UploadService;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘康
 * @create 2019-02-01 9:01
 * @desc 上传文件的控制器
 **/
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    public UploadService uploadService;

    @PostMapping("image")
    public ResultVO uploadImage(@RequestParam MultipartFile file){
        return uploadService.uploadFile(file, FileType.IMAGE);
    }
}
