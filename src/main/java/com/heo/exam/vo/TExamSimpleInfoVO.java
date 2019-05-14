package com.heo.exam.vo;

import com.heo.exam.enums.ExamStateEnum;
import com.heo.exam.utils.DateUtil;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-04-28 22:13
 * @desc 教师的考试简单信息列表
 **/
@Data
public class TExamSimpleInfoVO {

    private Integer id;

    private String name;

    private String desc;

    private String subject;

    private Integer state;

    private String stateName;

    private String beginTime;

    /**
     * 结束日期
     */
    private String endTime;

    /**
     * 已经完成的人
     */
    private Integer completeNum;

    /**
     * 考试中人数
     */
    private Integer examinationNum;

    /**
     * 未开始的人数
     */
    private Integer notStartNum;

    private Integer allNum;

    public TExamSimpleInfoVO(Integer examId, String name, String desc, String subject, Date beginTime, Date endTime,Integer time, Integer completeNum,Date submitTime, Integer examinationNum, Integer notStartNum, Integer allNum) {
        this.id = examId;
        this.name = name;
        this.desc = desc;
        this.subject = subject.substring(0, 1);

        Date now = new Date();
        if (beginTime.after(now)) {
            state = ExamStateEnum.NOT_START.getCode();
            stateName = ExamStateEnum.NOT_START.getName();
            this.beginTime = DateUtil.formatter(beginTime,"yyyy-MM-dd HH:mm");
        }

        if (allNum.equals(completeNum)){
            this.endTime = DateUtil.formatter(submitTime,"yyyy-MM-dd HH:mm");
            state = ExamStateEnum.END.getCode();
            stateName = ExamStateEnum.END.getName();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.MINUTE,time);
            this.endTime = DateUtil.formatter(calendar.getTime(),"yyyy-MM-dd HH:mm");
            if (endTime.before(now)){
                state = ExamStateEnum.END.getCode();
                stateName = ExamStateEnum.END.getName();
            }else{
                state = ExamStateEnum.HAVE_IN_HAND.getCode();
                stateName = ExamStateEnum.HAVE_IN_HAND.getName();
            }

        }

        this.completeNum = completeNum;
        this.examinationNum = examinationNum;
        this.notStartNum = notStartNum;
        this.allNum = allNum;
    }
}
