package com.heo.exam.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 刘康
 * @create 2019-02-01 15:15
 * @desc 班级信息表单
 **/
@Data
public class ClassInfoForm {

    @Size(max = 32,message = "名称过长")
    @NotNull(message = "名称未填写")
    private String name;

    @Size(max = 256,message = "图片url过长")
    private String avatar;

    @Size(max = 16,message = "密码太长了")
    private String password;

    @Max(value = 16,message = "年级不合法")
    @Min(value = 1,message = "年级不合法")
    private Integer grade;

    @Size(max = 256,message = "描述过长了")
    private String desc;
}
