package com.heo.exam.repository;

import com.heo.exam.entity.User2Class;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-02-01 14:23
 * @desc 学生对应班级表dao
 **/
public interface User2ClassRepository extends CrudRepository<User2Class,Integer>{

    boolean existsByUserIdAndClassId(String userId,String classId);

    User2Class findByUserIdAndClassId(String userId,String classId);

    Integer countByClassIdAndUserType(String classId,Integer userType);
}
