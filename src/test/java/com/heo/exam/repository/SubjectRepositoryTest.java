package com.heo.exam.repository;

import com.heo.exam.entity.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    @Transactional
    public void save(){
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(new Subject("语文"));
        subjectList.add(new Subject("数学"));
        subjectList.add(new Subject("英语"));
        subjectList.add(new Subject("物理"));
        subjectList.add(new Subject("化学"));
        subjectList.add(new Subject("生物"));
        subjectList.add(new Subject("地理"));
        subjectList.add(new Subject("历史"));
        subjectList.add(new Subject("政治"));

        subjectRepository.saveAll(subjectList);

        subjectRepository.count();
    }

    @Test
    public void delete(){
        subjectRepository.deleteAll();
    }

}