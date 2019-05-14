package com.heo.exam.service.impl;

import com.heo.exam.entity.Subject;
import com.heo.exam.repository.SubjectRepository;
import com.heo.exam.service.SubjectService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 刘康
 * @create 2019-04-28 21:56
 * @desc 科目业务逻辑
 **/
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public ResultVO getAllSubject() {
        List<Subject> all = subjectRepository.findAll();
        List<Map<String, Object>> collect = all.stream().map(subject -> subject.toMap()).collect(Collectors.toList());
        return ResultVOUtil.success(collect);
    }
}
