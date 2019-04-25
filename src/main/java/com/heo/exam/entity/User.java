package com.heo.exam.entity;

import com.heo.exam.constant.Default;
import com.heo.exam.enums.SexEnum;
import com.heo.exam.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-01-31 13:34
 * @desc 用户实体类
 **/
@Data
@Entity
public class User {

    @Id
    private String id;

    private String openid;

    private String name = "";

    private String username = "";

    private String password = Default.PASSWORD;

    private String avatar = "";

    private Integer type;

    private Integer sex = SexEnum.FEMALE.getCode();

    private String nid = "";

    private String phone = "";

    @Column(name = "user_desc")
    private String desc = "";

    @Transient
    private Integer likedNum;

    private Date createTime;

    private Date updateTime;

    public User(){}

    public User(String openid,int type){
        this.openid = openid;
        this.id = KeyUtil.genUniqueKey();
        this.type = type;
    }

}
