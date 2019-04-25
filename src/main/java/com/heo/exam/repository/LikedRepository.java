package com.heo.exam.repository;

import com.heo.exam.entity.Liked;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;


/**
 * @author 刘康
 * @create 2019-03-11 14:24
 * @desc 用户点赞dao
 **/
public interface LikedRepository extends CrudRepository<Liked,Integer> {

    @Query(value = "select us from Liked us where us.userId = ?1 and us.likedUserId = ?2 and us.createTime = current_date")
    Liked findTodayUserSupport(String userId, String likedUserId);

    @Query(value = "select  coalesce(SUM(us.num),0) from Liked us where us.likedUserId = ?1")
    Integer getLikeNumByLikedUserId(String userId);

    boolean existsByLikedUserIdAndCreateTime(String userId, Date now);

}
