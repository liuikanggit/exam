package com.heo.exam.repository;

import com.heo.exam.entity.Paper2Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-03 19:25
 * @desc 试卷对应题目dao
 **/
public interface Paper2QuestionRepository extends CrudRepository<Paper2Question, Integer> {

    @Query(value = "select pq.questionId from Paper2Question pq " +
            "left join com.heo.exam.entity.Question q on pq.questionId = q.id " +
            "where pq.paperId = ?1 " +
            "order by q.type")
    List<Integer> getAllQuestIonIdByPaperId(Integer paperId);
}
