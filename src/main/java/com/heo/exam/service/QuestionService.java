package com.heo.exam.service;

import com.heo.exam.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author 刘康
 * @create 2019-02-21 13:08
 * @desc 题目service
 **/
public interface QuestionService {

    /**
     * excel录入题目
     * @param file
     * @return
     */
    ResultVO excelInput(String userId,MultipartFile file);

    File getExcelTemplate();


}
