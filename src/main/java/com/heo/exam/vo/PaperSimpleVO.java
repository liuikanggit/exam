package com.heo.exam.vo;

import com.heo.exam.utils.DateUtil;
import lombok.Data;

import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-09 16:53
 * @desc 试卷的简单信息
 **/
@Data
public class PaperSimpleVO {

    private Integer id;

    private String name;

    private String creator;

    private String subject;

    private Integer score;

    private String desc;

    private String createTime;

    public PaperSimpleVO(Integer id, String name, String creator, String subject, Integer score, String desc, Date createTime) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.subject = subject;
        this.score = score;
        this.desc = desc;
        this.createTime = DateUtil.formatter(createTime,"yyyy-MM-dd");
    }
}
