package com.heo.exam.vo;

import com.heo.exam.enums.GradeEnum;
import com.heo.exam.enums.QuestionTypeEnum;
import com.heo.exam.utils.DateUtil;
import com.heo.exam.utils.EnumUtil;
import lombok.Data;

import java.util.Date;

/**
 * @Author 刘康
 * @Date 2019-05-25 20:40
 * @Description
 * @Revision
 **/
@Data
public class QuestionSimpleVO {

    private Integer id;

    private String title;

    private String type;

    private String subject;

    private String grade;

    private String creator;

    private String createTime;

    public QuestionSimpleVO(){}

    public QuestionSimpleVO(Integer id, String title, int type, String subject, int grade, String creator, Date createTime){
        this.id= id;
        this.title = title;
        this.type = EnumUtil.getName(QuestionTypeEnum.class,type);
        this.subject = subject.substring(0,1);
        this.grade = EnumUtil.getName(GradeEnum.class,grade);
        this.creator = creator;
        this.createTime = DateUtil.formatter(createTime,"yyyy-MM-dd HH:mm");
    }

}
