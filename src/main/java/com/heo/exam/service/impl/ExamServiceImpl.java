package com.heo.exam.service.impl;

import com.heo.exam.entity.Exam;
import com.heo.exam.entity.ExamBase;
import com.heo.exam.entity.ExamDetail;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.form.Answer;
import com.heo.exam.repository.*;
import com.heo.exam.service.ExamService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 刘康
 * @create 2019-04-09 17:57
 * @desc 考试的业务逻辑实现
 **/
@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamBaseRepository examBaseRepository;

    @Autowired
    private ExamDetailRepository examDetailRepository;

    @Autowired
    private User2ClassRepository user2ClassRepository;

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private Paper2QuestionRepository paper2QuestionRepository;

    @Autowired
    private QuestionRepository questionRepository;



    @Override
    @Transactional
    public ResultVO createExam(String teacherId, Integer paperId, String[] classIds, String name, String desc, Date beginTime, Date endTime, Integer time) {
        if (!paperRepository.existsById(paperId)) {
            throw new ExamException(ResultEnum.PAPER_NOT_EXIST);
        }
        Arrays.stream(classIds).forEach(classId -> {

            if (!classRepository.existsById(classId)) {
                throw new ExamException(ResultEnum.CLASS_EMPTY);
            }

            if (!user2ClassRepository.existsByUserIdAndClassId(teacherId, classId)) {
                throw new ExamException(ResultEnum.NO_AUTH);
            }
            String subject = paperRepository.getPaperSubject(paperId);
            ExamBase examBase = new ExamBase(paperId, classId, teacherId, name, desc, subject, beginTime, endTime, time);
            examBase = examBaseRepository.save(examBase);
            Integer examId = examBase.getId();

            List<Integer> questionList = paper2QuestionRepository.getAllQuestIonIdByPaperId(paperId);
            List<String> userIdList = user2ClassRepository.findStudentIdByClassId(classId);
            List<Exam> examList = userIdList.stream().map(userId -> {
                List<ExamDetail> examDetail = questionList.stream().map(questionId -> new ExamDetail(examId, userId, questionId, "")).collect(Collectors.toList());
                examDetailRepository.saveAll(examDetail);
                return new Exam(examId, userId);
            }).collect(Collectors.toList());
            examRepository.saveAll(examList);
        });
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO getExamSimpleInfoByStudent(String userId, Integer page, Integer size) {
        Page<ExamSimpleInfoVO> examSimpleInfoVOPage = examRepository.getExamSimpleInfoByStudentId(userId, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo<>(examSimpleInfoVOPage));
    }

    @Override
    @Transactional
    public ResultVO getEndExamSimpleInfoByStudent(String userId, Integer page, Integer size) {
        /** 把未开始且已经错过的置为结束，给上考试截止日期*/
        examRepository.setExamEnd(userId);

        /** 获取正在考试中，已经考试结束的,批改 */
        List<Exam> endExam = examRepository.getEndExam(userId);
        if (endExam != null) {
            endExam.stream().forEach(exam ->
                    submitExam(userId, exam.getExamId())
            );
        }

        /** 获取所有已经结束的考试 */
        Page<ExamSimpleInfoVO> exam = examRepository.getEndExamSimpleInfoByStudentId(userId, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo<ExamSimpleInfoVO>(exam));
    }

    @Override
    @Transactional
    public ResultVO getExamSimpleInfoByTeacher(String teacherId, Integer page, Integer size) {
        examRepository.setExamEnd();
        return ResultVOUtil.success(examRepository.getTExamSimpleInfoVO(teacherId,page,size));
    }

    @Override
    public ResultVO getExamDetailInfoByStudent(String userId, Integer examId) {
        if (!examBaseRepository.existsById(examId)) {
            throw new ExamException(ResultEnum.EXAM_NOT_EXIST);
        }
        ExamDetailVO examDetailVO = examRepository.getExamDetailVO(examId, userId);
        return ResultVOUtil.success(examDetailVO);
    }

    @Override
    public ResultVO getExamDetailInfoByTeacher(String userId, Integer examId) {
        return null;
    }

    @Override
    @Transactional
    public ResultVO startExam(String userId, Integer examId) {
        ExamQuestionVo examQuestionVo = new ExamQuestionVo();
        if (!examBaseRepository.existsById(examId)) {
            throw new ExamException(ResultEnum.EXAM_NOT_EXIST);
        }
        Calendar now = Calendar.getInstance();
        Date n = new Date();
        Exam exam = examRepository.getExamByExamIdAndUserId(examId, userId);

        if (exam.getSubmitTime()!=null){
            throw new ExamException(ResultEnum.EXAM_IS_END);
        }
        /** 根据是否有开始时间，来判断是开始考试，还是继续考试 */
        if (exam.getStartTime() == null) {
            /** 判断考试是否截止了 */
            ExamBase examBase = examBaseRepository.getById(examId);
            Date beginTime = examBase.getBeginTime();
            Date endTime = examBase.getEndTime();
            if (n.before(beginTime)) {
                throw new ExamException(ResultEnum.EXAM_NOT_START);
            }
            if (n.after(endTime)) {
                throw new ExamException(ResultEnum.EXAM_IS_END);
            }

            exam.setStartTime(now.getTime());
            Integer time = examBaseRepository.getTimeByExamId(examId);
            now.add(Calendar.MINUTE, time);
            exam.setEndTime(now.getTime());
            examRepository.save(exam);
        } else {
            Date endTime = exam.getEndTime();
            if (n.after(endTime)) {
                // 考试已经截止了
                submitExam(userId, examId);
                throw new ExamException(ResultEnum.EXAM_IS_END);
            }
        }
        List<QuestionVO> questionVOList = examDetailRepository.getQuestionVo(examId, userId);
        examQuestionVo.setId(examId);
        String name = examBaseRepository.getNameByExamId(examId);
        examQuestionVo.setName(name);
        long time = (exam.getEndTime().getTime() - new Date().getTime())/1000;//剩余时间 秒
        if (time <= 0) {
            /** 没时间了，自动交卷 */
            submitExam(userId, examId);
            throw new ExamException(ResultEnum.EXAM_IS_END);
        }
        examQuestionVo.setQuestions(questionVOList);
        examQuestionVo.setTime(time);
        return ResultVOUtil.success(examQuestionVo);
    }

    @Override
    @Transactional
    public ResultVO saveExam(String userId, Integer examId, Map<String,String > answerMap) {
        /** 更新答题情况 */
        if (answerMap!=null) {
            List<ExamDetail> examDetailList = examDetailRepository.getAllByExamIdAndUserId(examId, userId);
            examDetailList = examDetailList.stream().map(ed -> {
                String answer = answerMap.get(ed.getQuestionId());
                if (answer == null) {
                   throw new ExamException(ResultEnum.SYSTEM_EXCEPTION);
                }
                ed.setAnswer(answer);
                return ed;
            }).collect(Collectors.toList());
            examDetailRepository.saveAll(examDetailList);
        }
        /** 返回时间 */
        Exam exam = examRepository.getExamByExamIdAndUserId(examId, userId);
        long time = exam.getEndTime().getTime() - new Date().getTime();//剩余时间
        if (time <= 0) {
            /** 没时间了，自动交卷 */
            submitExam(userId, examId);
            return ResultVOUtil.success(0);
        } else {
            /** 返回剩余时间 */
            return ResultVOUtil.success(time / 1000);
        }
    }


    @Override
    @Transactional
    public ResultVO submitExam(String userId, Integer examId) {
        Exam exam = examRepository.getExamByExamIdAndUserId(examId, userId);
        /** 判断考试是否已经被提交过了 */
        if (exam.getSubmitTime() != null) {
            throw new ExamException(ResultEnum.EXAM_IS_END);
        }
        Date now = new Date();
        List<ExamDetail> examDetailList;
        examDetailList = examDetailRepository.getAllByExamIdAndUserId(examId, userId);

        /** 计算分数 */
        BigDecimal score = BigDecimal.ZERO;
        long total = examDetailList.size();
        if (total > 0) {
            Integer totalScore = examBaseRepository.getTotalScoreById(examId);
            long correct = examDetailList.stream().filter(examDetail ->
                    questionRepository.existsByIdAndAnswer0(examDetail.getQuestionId(), examDetail.getAnswer())
            ).count();
            score = new BigDecimal(correct).divide(new BigDecimal(total)).multiply(new BigDecimal(totalScore));
        }

        /** 更改考试提交时间和对应的分数 */
        exam.setSubmitTime(now);
        exam.setScore(score);
        examRepository.save(exam);
        log.info("试卷批改完成. userId:{} examId:{} score:{}", userId, examId, score);
        return ResultVOUtil.success();
    }

}
