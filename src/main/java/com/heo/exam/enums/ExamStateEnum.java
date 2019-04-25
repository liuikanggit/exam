package com.heo.exam.enums;

/**
 * @author 刘康
 * @create 2019-04-14 15:32
 * @desc 考试状态
 **/
public enum  ExamStateEnum implements EnumCommon {

    NOT_START(0,"未开始"),
    HAVE_IN_HAND(1,"进行中"),
    EXAM_ING(2,"考试中"),
    END(3,"以完成"),
    MISS(4,"缺考")
    ;

    private Integer code;

    private String name;

    ExamStateEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
