package com.heo.exam.repository;

import com.heo.exam.entity.ExamDetail;
import com.heo.exam.vo.QuestionVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-10 15:22
 * @desc
 **/
public interface ExamDetailRepository extends CrudRepository<ExamDetail,Integer> {

    @Query(value = "select new com.heo.exam.vo.QuestionVO(q.id,q.type,q.title,q.titleImage," +
            "q.answer0,q.answerImage0,q.answer1,q.answerImage1,q.answer2,q.answerImage2,q.answer3,q.answerImage3," +
            "ed.answer) " +
            "from ExamDetail ed " +
            "left join com.heo.exam.entity.Question q on q.id = ed.questionId " +
            "where ed.examId = ?1 and ed.userId = ?2 " +
            "order by q.type")
    List<QuestionVO> getQuestionVo(Integer examId,String userId);

    List<ExamDetail> getAllByExamIdAndUserId(Integer examId,String userId);

}
