package com.heo.exam.service;

import com.heo.exam.entity.ExamDetail;
import com.heo.exam.form.Answer;
import com.heo.exam.vo.ResultVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘康
 * @create 2019-04-09 17:41
 * @desc 考试业务逻辑
 **/
public interface ExamService {

    /**
     * 创建考试
     *
     * @param teacherId 教师id
     * @param paperId   试卷id
     * @param name      考试名称
     * @param desc      考试描述
     * @param classIds  班级id列表
     * @param beginTime 容许加入考试的试卷
     * @param endTime   容许加入考试的截止时间
     * @param time      考试时长
     * @return 结果
     */
    ResultVO createExam(String teacherId, Integer paperId, String[] classIds, String name, String desc, Date beginTime, Date endTime, Integer time);

    /**
     * 获取考试信息列表，分页
     *
     * @param userId
     * @return
     */
    ResultVO getExamSimpleInfoByStudent(String userId, Integer page, Integer size);

    ResultVO getEndExamSimpleInfoByStudent(String userId,Integer page,Integer size);

    /**
     * 获取考试信息列表分页
     *
     * @param teacherId
     * @return
     */
    ResultVO getExamSimpleInfoByTeacher(String teacherId, Integer page, Integer size);

    /**
     * 获取考试详细信息
     *
     * @param userId
     * @return
     */
    ResultVO getExamDetailInfoByStudent(String userId, Integer examId);

    /**
     * 教师获取考试详细信息
     *
     * @param userId
     * @return
     */
    ResultVO getExamDetailInfoByTeacher(String userId, Integer examId);

    /**
     * 开始考试 (添加开始时间，获取题目)
     *
     * @param userId
     * @return
     */
    ResultVO startExam(String userId, Integer examId);

    /**
     * 中途保存题目
     *
     * @param userId     用户id
     * @param examId     考试id
     * @param answerMap 答题情况
     * @return
     */
    ResultVO saveExam(String userId, Integer examId, Map<String,String > answerMap);

    /**
     * 提交考试 (添加提交时间，获取分数)
     *
     * @param userId
     * @return
     */
    ResultVO submitExam(String userId, Integer examId);


}
