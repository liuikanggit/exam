package com.heo.exam.repository;

import com.heo.exam.entity.ExamBase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-04-09 18:07
 * @desc
 **/
public interface ExamBaseRepository extends CrudRepository<ExamBase,Integer> {
    @Query(value = "select eb.time from ExamBase eb where eb.id = ?1")
    Integer getTimeByExamId(Integer examId);

    @Query(value = "select eb.name from ExamBase eb where eb.id = ?1")
    String getNameByExamId(Integer examId);

    ExamBase getById(Integer examId);


    @Query(value = "select p.score from ExamBase eb " +
            "left join com.heo.exam.entity.Paper p on p.id = eb.paperId " +
            "where eb.id = ?1")
    Integer getTotalScoreById(Integer id);
}
