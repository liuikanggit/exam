package com.heo.exam.vo;

import com.heo.exam.entity.Clazz;
import com.heo.exam.enums.GradeEnum;
import com.heo.exam.utils.EnumUtil;
import lombok.Data;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-03-28 10:18
 * @desc 班级和用户信息
 **/
@Data
public class ClassAndUserInfo {

    private String id;

    private String name;

    private String avatar;

    private String grade;

    private String desc;

    private List<UserSimpleInfo> teacher; //第一个是班主任

    private List<UserSimpleInfo> student;

    public ClassAndUserInfo(Clazz clazz) {
        this.id = clazz.getId();
        this.name = clazz.getName();
        this.avatar = clazz.getAvatar();
        this.grade = EnumUtil.getName(GradeEnum.class, clazz.getGrade());
        this.desc = clazz.getDesc();
    }

}
