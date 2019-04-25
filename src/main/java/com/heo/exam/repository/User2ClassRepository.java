package com.heo.exam.repository;

import com.heo.exam.entity.User2Class;
import com.heo.exam.vo.ClassSimpleInfoVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select uc.userId from User2Class uc where uc.classId = ?1 and uc.userType = 0 ")
    List<String> findStudentIdByClassId(String classId);

    User2Class findByUserIdAndClassId(String userId, String classId);

    Integer countByClassIdAndUserType(String classId, Integer userType);

    @Query(value = "select uc.classId from User2Class uc where uc.userId = ?1",
            countQuery = "select COUNT(id) from User2Class where userId = ?1")
    Page<String> findClassIdByUserId(String userId, PageRequest pageRequest);


    @Query(value = "select new com.heo.exam.vo.ClassSimpleInfoVO(uc.classId,c.name,c.avatar,uc.userId,c.creatorId,uc.createTime) " +
            "from User2Class uc " +
            "left join com.heo.exam.entity.Clazz c on uc.classId = c.id " +
            "where uc.userId = ?1 " +
            "order by nullif(c.creatorId,uc.userId) ,uc.createTime desc ",
            countQuery = "select COUNT(id) from User2Class where userId = ?1")
    Page<ClassSimpleInfoVO> findJoinClassSimpleInfo(String userId, Pageable pageable);

    @Modifying
    void deleteByClassIdAndUserId(String classId,String userId);
}
