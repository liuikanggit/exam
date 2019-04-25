package com.heo.exam.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heo.exam.enums.ExamStateEnum;
import com.heo.exam.utils.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-10 15:35
 * @desc 考试简单信息
 **/
@Data
public class ExamSimpleInfoVO {

    private Integer id;

    /**
     * 考试名称
     */
    private String name;

    /**
     * 考试描述
     */
    private String desc;

    /**
     * 考试科目
     */
    private String subject;

    /**
     * 考试时长(分钟)
     */
    private Integer time;

    private String beginTime;

    private String endTime;

    private String submitTime;

    private Integer remainingTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal score;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String startTime;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 状态名称
     */
    private String stateName;

    public ExamSimpleInfoVO(Integer id, String name, String desc, String subject, Integer time, Date beginTime, Date endTime, Date endSubmitEnd) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.subject = subject.substring(0, 1);
        this.time = time;
        this.beginTime = DateUtil.formatter(beginTime, "yyyy-MM-dd HH:mm");
        this.endTime = DateUtil.formatter(endTime, "yyyy-MM-dd HH:mm");
        if (beginTime.before(new Date())) {
            if (endSubmitEnd != null) {
                this.state = ExamStateEnum.EXAM_ING.getCode();
                this.stateName = ExamStateEnum.EXAM_ING.getName();
                this.submitTime = DateUtil.formatter(endSubmitEnd, "yyyy-MM-dd HH:mm");
                this.remainingTime = Math.toIntExact((endSubmitEnd.getTime() - new Date().getTime()) / 1000 / 60);
            } else {
                this.state = ExamStateEnum.HAVE_IN_HAND.getCode();
                this.stateName = ExamStateEnum.HAVE_IN_HAND.getName();
            }
        } else {
            this.state = ExamStateEnum.NOT_START.getCode();
            this.stateName = ExamStateEnum.NOT_START.getName();
        }
    }

    public ExamSimpleInfoVO(Integer id, String name, String desc, String subject, BigDecimal score, Date stateTime,Date submitTime) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.subject = subject.substring(0, 1);
        this.score = score;
        if (stateTime == null){
            this.state = ExamStateEnum.MISS.getCode();
            this.stateName = ExamStateEnum.MISS.getName();
        }else{
            this.state = ExamStateEnum.END.getCode();
            this.stateName = ExamStateEnum.END.getName();
        }
        this.submitTime = DateUtil.formatter(submitTime, "yyyy-MM-dd HH:mm");

    }
}
