package com.heo.exam.repository;

import com.heo.exam.entity.Exam;
import com.heo.exam.entity.ExamDetail;
import com.heo.exam.vo.ExamDetailVO;
import com.heo.exam.vo.ExamSimpleInfoVO;
import com.heo.exam.vo.QuestionVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-08 15:02
 * @desc 考试dao
 **/
public interface ExamRepository extends CrudRepository<Exam, Integer> {

    @Query(value = "select new com.heo.exam.vo.ExamSimpleInfoVO(eb.id,eb.name,eb.desc,eb.subject,eb.time,eb.beginTime,eb.endTime,e.endTime) from Exam e " +
            "left join com.heo.exam.entity.ExamBase eb on eb.id = e.examId " +
            "where e.userId = ?1 and ((e.startTime is null and current_timestamp < eb.endTime) or e.endTime > current_timestamp) and e.submitTime is null " +
            "order by e.endTime desc ,eb.beginTime",
            countQuery = "select count(e) from Exam e " +
                    " left join com.heo.exam.entity.ExamBase eb on eb.id = e.examId " +
                    "where e.userId = ?1 and ((e.startTime is null and current_timestamp < eb.endTime) or e.endTime > current_timestamp) and e.submitTime is null ")
    Page<ExamSimpleInfoVO> getExamSimpleInfoByStudentId(String studentId, PageRequest pageRequest);

    @Query(value = "select e from Exam e " +
            "where e.userId = ?1 " +
            "and e.submitTime is null " +
            "and e.startTime is not null and current_timestamp >= e.endTime "
            )
    List<Exam> getEndExam(String userId);

    @Modifying
    @Query(value = "update Exam e " +
            "set e.submitTime = current_timestamp, " +
            "e.startTime = null " +
            "where e.userId = ?1 " +
            "and e.submitTime is null " +
            "and e.startTime is null " +
            "and current_timestamp >= (select eb.endTime from com.heo.exam.entity.ExamBase eb where e.examId = eb.id) ")
    void setExamEnd(String userId);

    @Query(value = "select new com.heo.exam.vo.ExamSimpleInfoVO(eb.id,eb.name,eb.desc,eb.subject,e.score,e.startTime,e.submitTime) from Exam e " +
            "left join com.heo.exam.entity.ExamBase eb on eb.id = e.examId " +
            "where e.userId = ?1 and e.submitTime is not null " +
            "order by e.submitTime desc ",
            countQuery = "select count(e) from Exam e where e.userId = ?1 and e.submitTime is not null")
    Page<ExamSimpleInfoVO> getEndExamSimpleInfoByStudentId(String studentId, PageRequest pageRequest);

    @Query(value = "select new com.heo.exam.vo.ExamDetailVO(e.examId,eb.name,eb.classId,c.name,eb.subject,eb.desc,eb.time,eb.creatorId,u.name,eb.beginTime,eb.endTime,p.score,e.startTime,e.submitTime,e.endTime,e.score) " +
            "from Exam e " +
            "left join com.heo.exam.entity.ExamBase eb on eb.id = e.examId " +
            "left join com.heo.exam.entity.Clazz c on c.id = eb.classId " +
            "left join com.heo.exam.entity.User u on u.id = eb.creatorId " +
            "left join com.heo.exam.entity.Paper p on p.id = eb.paperId " +
            "where e.examId = ?1 and  e.userId = ?2 " +
            "order by e.submitTime desc "
    )
    ExamDetailVO getExamDetailVO(Integer examId, String userId);

    Exam getExamByExamIdAndUserId(Integer examId, String userId);

    @Query(value = "select e from Exam e " +
            "left join com.heo.exam.entity.ExamBase eb " +
            "where (e.startTime is null and current_timestamp > eb.endTime) or (e.endTime < current_timestamp )")
    List<Exam> getAllEndExam();

    boolean existsByExamIdAndUserId(Integer examId, String userId);

}
