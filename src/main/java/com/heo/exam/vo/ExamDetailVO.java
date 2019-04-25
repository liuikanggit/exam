package com.heo.exam.vo;

import com.heo.exam.enums.ExamStateEnum;
import com.heo.exam.utils.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-14 15:30
 * @desc 考试详细信息
 **/
@Data
public class ExamDetailVO {
    /**
     * 考试id
     */
    private Integer id;

    /**
     * 考试名称
     */
    private String name;

    /**
     * 班级id
     */
    private String classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 考试科目
     */
    private String subject;

    /**
     * 考试描述
     */
    private String desc;

    /**
     * 考试时长
     */
    private Integer time;

    /**
     * 创建者id
     */
    private String creatorId;

    /**
     * 创建者名称
     */
    private String creatorName;

    /**
     * 容许进入考试开始时间
     */
    private String beginTime;

    /**
     * 停止进入考试时间
     */
    private String endTime;

    /**
     * 考试总分
     */
    private Integer totalScore;

    /**
     * 考试状态
     */
    private Integer state;
    private String stateName;

    /**
     * 考试考试时间
     */
    private String startTime;

    private Integer remainingTime;

    /**
     * 交卷时间
     */
    private String submitTime;

    /**
     * 得分
     */
    private BigDecimal score;

    public ExamDetailVO(Integer id, String name, String classId, String className, String subject, String desc, Integer time, String creatorId, String creatorName, Date beginTime, Date endTime, Integer totalScore, Date startTime, Date submitTime, Date endSubmitTime, BigDecimal score) {
        this.id = id;
        this.name = name;
        this.classId = classId;
        this.className = className;
        this.subject = subject;
        this.desc = desc;
        this.time = time;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.beginTime = DateUtil.formatter(beginTime,"yyy-MM-dd HH:mm");
        this.endTime =
                DateUtil.formatter(endTime,"yyy-MM-dd HH:mm");
        this.totalScore = totalScore;

        /** 状态分为 0:未开始  1.进行中 2.考试中 3.截止了 */
        Date now = new Date();
        if (beginTime.after(now)) { // 未开始
            this.state = ExamStateEnum.NOT_START.getCode();
            this.stateName = ExamStateEnum.NOT_START.getName();
            this.remainingTime = Math.toIntExact((beginTime.getTime() - now.getTime()) / 1000 / 60);
            if (this.remainingTime==0){
                this.remainingTime=1;
            }
        } else if (startTime == null && endTime.before(now) || submitTime != null || (startTime!=null&&endSubmitTime.before(now))) {
            //考试截止分3中情况
            //1. 未开始考试，但是已经错过了进入考试的试卷(缺考)
            //2. 试卷已经提交了
            //3. 考试时间结束了
            if (startTime != null) {
                this.state = ExamStateEnum.END.getCode();
                this.stateName = ExamStateEnum.END.getName();
                this.startTime = DateUtil.formatter(startTime, "yyyy-MM-dd HH:mm");
            }else{
                this.state = ExamStateEnum.MISS.getCode();
                this.stateName = ExamStateEnum.MISS.getName();
            }
            if (submitTime != null) {
                this.submitTime = DateUtil.formatter(submitTime,"yyyy-MM-dd HH:mm");
            }else{
                this.submitTime = DateUtil.formatter(endSubmitTime,"yyyy-MM-dd HH:mm");
            }
            this.score = score;
        }else if (startTime == null){
            /** 进行中 */
            this.state = ExamStateEnum.HAVE_IN_HAND.getCode();
            this.stateName = ExamStateEnum.HAVE_IN_HAND.getName();
        }else{
            /** 考试中 */
            this.state = ExamStateEnum.EXAM_ING.getCode();
            this.stateName = ExamStateEnum.EXAM_ING.getName();
            this.startTime = DateUtil.formatter(startTime,"yyyy-MM-dd HH:mm");
            this.submitTime = DateUtil.formatter(endSubmitTime,"yyyy-MM-dd HH:mm");
            this.remainingTime = Math.toIntExact((endSubmitTime.getTime() - now.getTime()) / 1000 / 60);
            if (this.remainingTime==0){
                this.remainingTime=1;
            }
        }
    }
}
