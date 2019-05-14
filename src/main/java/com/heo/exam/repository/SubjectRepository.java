package com.heo.exam.repository;

import com.heo.exam.entity.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-20 22:02
 * @desc 科目dao
 **/
@Repository
public interface SubjectRepository extends CrudRepository<Subject,Integer> {

    @Query(value = "select name from Subject")
    List<String> findAllName();

    @Query(value = "select id from Subject where name = ?1")
    Integer getOneIdByName(String name);

    @Query(value = "select s from Subject s")
    List<Subject> findAll();
}
