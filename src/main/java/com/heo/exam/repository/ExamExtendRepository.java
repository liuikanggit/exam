package com.heo.exam.repository;

import com.heo.exam.vo.PageVo;
import com.heo.exam.vo.TExamSimpleInfoVO;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-30 17:43
 * @desc
 **/
public interface ExamExtendRepository {

    PageVo<List<TExamSimpleInfoVO>> getTExamSimpleInfoVO(String teacherId,Integer page,Integer size);
}
