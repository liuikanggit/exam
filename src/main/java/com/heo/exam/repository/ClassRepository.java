package com.heo.exam.repository;

import com.heo.exam.entity.Clazz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-01 14:22
 * @desc 班级dao
 **/
@Repository
public interface ClassRepository extends CrudRepository<Clazz, String> {

    boolean existsById(String id);

    Clazz getById(String id);

    List<Clazz> findAllByIdIn(List<String> ids);

    boolean existsByNameAndCreatorId(String name,String userId);

    boolean existsByIdAndCreatorId(String id,String userId);

}
