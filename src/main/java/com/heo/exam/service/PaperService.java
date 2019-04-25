package com.heo.exam.service;

import com.heo.exam.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-04-03 19:01
 * @desc 对试卷的操作的服务
 **/
public interface PaperService {

    /**
     * 分页查看所有试卷
     * 如果teacherId有值，查看该教师创建的
     */
    ResultVO getAllPaper(String teacherId,Integer subject,Integer grade, int page, int size);

    /**
     * 创建（修改）试卷
     * 如过试卷id有值,则删除，新建一个
     *
     * @param pagerId   试卷id
     * @param teacherId 创建的教师
     * @param subjectId 科目id
     * @param name      名称
     * @param score     总分
     * @param desc      描述
     * @param question  所有的题目
     * @return
     */
    ResultVO savePaper(Integer pagerId, String teacherId, Integer subjectId, Integer grade,String name, Integer score, String desc, Integer question[]);

    /**
     * 删除试卷
     *
     * @param pagerId 试卷id
     * @return
     */
    ResultVO deletePaper(Integer pagerId);
}
