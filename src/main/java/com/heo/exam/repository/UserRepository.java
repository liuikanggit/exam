package com.heo.exam.repository;

import com.heo.exam.entity.User;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.vo.UserSimpleInfo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-01-31 14:28
 * @desc 用户dao
 **/
@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User getUserById(String userId);

    @Query("select id from User where openid = ?1 and type = ?2")
    String getIdByOpenidAndType(String id, Integer type);

    @Query("select openid from User where id = ?1")
    String getOpenidById(String id);


    User findByUsernameAndType(String username, Integer type);

    @Modifying
    @Query("update User set name = :#{#info.name}, phone = :#{#info.phone} ," +
            " avatar = :#{#info.avatar} , nid = :#{#info.nid}, sex = :#{#info.sex}, user_desc = :#{#info.desc} " +
            "where id = :id")
    void updateUserInfo(@Param("id") String userId, @Param("info") UserInfoForm userInfoForm);

    boolean existsByIdAndType(String id, Integer type);

    @Query("select u.name from User u where id = ?1")
    String getNameById(String id);

    List<User> getUsersByIdInOrderByTypeDesc(List<String> ids);

    @Query(value = "select new com.heo.exam.vo.UserSimpleInfo(u.id,u.name,u.avatar,u.desc) " +
            "from com.heo.exam.entity.User2Class uc left join  User u on u.id = uc.userId " +
            "where uc.classId = ?1 and uc.userType = ?2 order by uc.createTime")
    List<UserSimpleInfo> findAllUserSimpleInfoByClassIdAndUserType(String classId, Integer userType);

}
