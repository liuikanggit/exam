package com.heo.exam.repository;

import com.heo.exam.entity.Clazz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-01 14:22
 * @desc 班级dao
 **/
public interface ClassRepository extends CrudRepository<Clazz,String> {

    boolean existsById(String id);
}
