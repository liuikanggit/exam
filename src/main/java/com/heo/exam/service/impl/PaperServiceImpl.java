package com.heo.exam.service.impl;

import com.heo.exam.entity.Paper;
import com.heo.exam.entity.Paper2Question;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.repository.Paper2QuestionRepository;
import com.heo.exam.repository.PaperRepository;
import com.heo.exam.repository.SubjectRepository;
import com.heo.exam.service.PaperService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.PageVo;
import com.heo.exam.vo.PaperSimpleVO;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 刘康
 * @create 2019-04-03 19:12
 * @desc 试卷服务实现类
 **/
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private Paper2QuestionRepository paper2QuestionRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public ResultVO getAllPaper(String teacherId,Integer subject,Integer grade, int page, int size) {
        Page<PaperSimpleVO> paperSimpleByTeacher = paperRepository.getPaperSimple(teacherId,subject,grade, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo<PaperSimpleVO>(paperSimpleByTeacher));
    }

    @Override
    @Transactional
    public ResultVO savePaper(Integer paperId, String creatorId, Integer subjectId,Integer gradeCode, String name, Integer score, String desc, Integer[] questions) {
        if (paperId != null) {
            paperRepository.deleteById(paperId);
        }

        /** 1 验证科目是否合法 */
        if ( ! subjectRepository.existsById(subjectId)) {
            throw new ExamException(ResultEnum.SUBJECT_NOT_EXIST);
        }

        Paper paper = new Paper(creatorId, subjectId,gradeCode, name, score, desc);
        paper = paperRepository.save(paper);
        paperId = paper.getId();
        Integer finalPaperId = paperId;

        List<Paper2Question> paper2QuestionList = Arrays.stream(questions).map(q -> new Paper2Question(finalPaperId, q)).collect(Collectors.toList());
        paper2QuestionRepository.saveAll(paper2QuestionList);
        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO deletePaper(Integer paperId) {
        paperRepository.deleteById(paperId);
        return ResultVOUtil.success();
    }
}
