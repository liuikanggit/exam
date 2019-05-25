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

    /**
     * 创建选择题
     * @param subjectId 科目
     * @param creatorId 创建人
     * @param grade 年级
     * @param title 题目名
     * @param answer0 答案
     * @param answer1 错误答案
     * @param answer2 错误答案
     * @param answer3 错误答案
     * @param analysis 解析
     * @return 结果
     */
    ResultVO inputChoiceQuestion(Integer subjectId,String creatorId,int grade,String title,String answer0,String answer1,String answer2,String answer3,String analysis);

    /**
     * 创建判断题
     * @param subjectId 科目id
     * @param creatorId 创建人
     * @param grade 年级
     * @param title 题目
     * @param answer 对 or 错
     * @param analysis 解析
     * @return 结果
     */
    ResultVO inputJudgementQuestion(Integer subjectId,String creatorId,int grade,String title,boolean answer,String analysis);


    ResultVO getAllQuestion();
}
