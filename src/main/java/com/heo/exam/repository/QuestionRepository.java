package com.heo.exam.repository;

import com.heo.exam.entity.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 刘康
 * @create 2019-02-22 10:23
 * @desc 题目dao
 **/
@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
    boolean existsQuestionByTitle(String title);
}
