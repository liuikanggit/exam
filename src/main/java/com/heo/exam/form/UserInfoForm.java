package com.heo.exam.form;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 刘康
 * @create 2019-02-01 11:09
 * @desc 修改用户信息表单类
 **/
@Data
public class UserInfoForm {

    @NotNull(message = "名称未填写")
    @Size(max = 32,message = "名称过长")
    public String name;

    @Size(max = 20,message = "联系方式过长")
    private String phone;

    @NotNull(message = "性别未填写")
    @Digits(integer = 1,fraction = 0,message = "性别参数不合法")
    private Integer sex;

    @Size(max = 256,message = "头像url过长")
    private String avatar;

    @Size(max = 20,message = "学号过长")
    private String nid;

}
