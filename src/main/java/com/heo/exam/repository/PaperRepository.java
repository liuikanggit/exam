package com.heo.exam.repository;

import com.heo.exam.entity.Paper;
import com.heo.exam.vo.PaperSimpleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author 刘康
 * @create 2019-04-03 19:00
 * @desc 试卷的repository操作
 **/
public interface PaperRepository extends CrudRepository<Paper, Integer> {

    @Query(value = "select new com.heo.exam.vo.PaperSimpleVO(p.id,p.name,u.name,s.name,p.score,p.desc,p.updateTime) from Paper p " +
            "left join com.heo.exam.entity.User u on p.creatorId = u.id " +
            "left join com.heo.exam.entity.Subject s on p.subjectId = s.id " +
            "where (?1 is null or p.creatorId = ?1) and (?2 is null or p.subjectId = ?2) and (?3 is null or p.gradeCode = ?3)",
            countQuery = "select count(p) from Paper p where (?1 is null or p.creatorId = ?1) and (?2 is null or p.subjectId = ?2) and (?3 is null or p.gradeCode = ?3)")
    Page<PaperSimpleVO> getPaperSimple(String teacherId, Integer subject, Integer grade, PageRequest pageRequest);

    @Query(value = "select s.name from Paper p " +
            "left join com.heo.exam.entity.Subject s on p.subjectId = s.id " +
            "where p.id = ?1")
    String getPaperSubject(Integer paperId);
}
