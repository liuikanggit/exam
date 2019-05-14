package com.heo.exam.repository.impl;

import com.heo.exam.repository.ExamExtendRepository;
import com.heo.exam.vo.PageVo;
import com.heo.exam.vo.TExamSimpleInfoVO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘康
 * @create 2019-04-30 17:40
 * @desc
 **/
@Repository
public class ExamRepositoryImpl implements ExamExtendRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PageVo<List<TExamSimpleInfoVO>> getTExamSimpleInfoVO(String teacherId,Integer page,Integer size) {
        PageVo pageVo = new PageVo();
        String sql = "select count(1) from  exam_base eb where eb.creator_id ='" + teacherId+"'";
        Integer total = ((BigInteger)entityManager.createNativeQuery(sql).getSingleResult()).intValue();

        sql = "select eb.id," +
                "       eb.name, " +
                "       eb.exam_desc, " +
                "       eb.subject, " +
                "       eb.begin_time, " +
                "       eb.end_time, " +
                "       eb.time, " +
                "       IFNULL(e1.num,0), " +
                "       e1.submit_time, " +
                "       IFNULL(e2.num,0), " +
                "       IFNULL(e3.num,0), " +
                "       IFNULL(e4.num,0)" +
                "from exam_base eb " +
                "left join (select exam_id,count(1) num ,MAX(submit_time) submit_time from exam where submit_time is not null group by exam_id) e1 on e1.exam_id = eb.id " +
                "left join (select exam_id,count(1) num from exam where start_time is not null and submit_time is null group by exam_id) e2 on e2.exam_id = eb.id " +
                "left join (select exam_id,count(1) num from exam where start_time is null and submit_time is  null group by exam_id) e3 on e3.exam_id = eb.id " +
                "left join (select exam_id,count(1) num from exam group by exam_id) e4 on e4.exam_id = eb.id " +
                "where eb.creator_id = "+teacherId +" " +
                "order by eb.end_time desc ";
        Query query = entityManager.createNativeQuery(sql);
        query.setFirstResult(page * size).setMaxResults(size);
        List<Object[]> resultList = query.getResultList();
        List<TExamSimpleInfoVO> tExamSimpleInfoVOList = resultList.stream().map(objects -> new TExamSimpleInfoVO((Integer) objects[0],(String) objects[1],(String) objects[2],(String)objects[3],(Date) objects[4],(Date)objects[5],((Integer) objects[6]),((BigInteger) objects[7]).intValue(),(Date)objects[8],((BigInteger) objects[9]).intValue(),((BigInteger) objects[10]).intValue(),((BigInteger) objects[11]).intValue())).collect(Collectors.toList());

        pageVo.setTotalData(total);
        pageVo.setTotalPages((total  +  size  - 1) / size);
        pageVo.setData(tExamSimpleInfoVOList);
        return pageVo;
    }
}
