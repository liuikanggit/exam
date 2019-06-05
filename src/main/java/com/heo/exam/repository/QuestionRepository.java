package com.heo.exam.repository;

import com.heo.exam.entity.Question;
import com.heo.exam.vo.QuestionSimpleVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-22 10:23
 * @desc 题目dao
 **/
@Repository
public interface QuestionRepository extends CrudRepository<Question,Integer> {
    boolean existsQuestionByTitle(String title);

    boolean existsByIdAndAnswer0(Integer question,String answer);


    List<Question> findAll();

    @Query(value = "select new com.heo.exam.vo.QuestionSimpleVO(q.id,q.title,q.type,s.name,q.grade,u.name,q.createTime)" +
            " from Question q " +
            "left join com.heo.exam.entity.Subject s on q.subjectId = s.id " +
            "left join com.heo.exam.entity.User u on q.creatorId = u.id")
    List<QuestionSimpleVO> findAllQuestionSimpleVO();


}
