package com.heo.exam.repository;

import com.heo.exam.entity.User2Class;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author 刘康
 * @create 2019-02-01 14:23
 * @desc 学生对应班级表dao
 **/
@Repository
public interface User2ClassRepository extends CrudRepository<User2Class, Integer> {

    boolean existsByUserIdAndClassId(String userId, String classId);

    @Query(value = "select uc.userId from User2Class uc where uc.classId = ?1 order by uc.userType desc ")
    List<String> findUserIdByClassId(String classId);

    User2Class findByUserIdAndClassId(String userId, String classId);

    Integer countByClassIdAndUserType(String classId, Integer userType);

    @Query(value = "select uc.classId from User2Class uc where uc.userId = ?1",
            countQuery = "select COUNT(id) from User2Class where userId = ?1")
    Page<String> findClassIdByUserId(String userId, PageRequest pageRequest);
}
