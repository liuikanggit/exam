package com.heo.exam.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.heo.exam.entity.Clazz;
import com.heo.exam.enums.GradeEnum;
import com.heo.exam.utils.DateSerializer;
import com.heo.exam.utils.EnumUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-01 14:17
 * @desc 班级信息
 **/
@Data
public class ClassInfoVO {

    /**
     * 班级id号
     */
    private String id;

    /**
     * 老师
     */
    private String creator;

    /**
     * 教师名称
     */
    private String creatorName;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 班级头像
     */
    private String avatar;

    /**
     * 是否有密码
     */
    private boolean isLock;

    /**
     * 密码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    /**
     * 教师数
     */
    private Integer teacherNumber;

    /**
     * 学生人数
     */
    private Integer studentNumber;

    /**
     * 年级
     */
    private String grade;

    /**
     * 描述
     */
    private String desc;

    /** 是否在班级里 */
    private boolean isInClass;

    /**
     * 创建日期
     */
    @JsonSerialize(using = DateSerializer.class)
    private Date createTime;

    public void hidePassword() {
        this.password = null;
    }

    public ClassInfoVO() {
    }

    public ClassInfoVO(Clazz clazz) {
        BeanUtils.copyProperties(clazz, this);
        this.grade = EnumUtil.getName(GradeEnum.class, clazz.getGrade());
    }

}
