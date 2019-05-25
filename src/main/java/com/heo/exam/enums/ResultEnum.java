package com.heo.exam.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数不正确"),

    STUDENT_EMPTY(100, "学生不存在"),
    LIKE_EXCEED_10(101,"每天最多给别人点赞10个"),
    LIKE_EXCEED_5(102,"每天最多给自己点赞5个"),
    TEACHER_EMPTY(200, "教师不存在"),
    USER_EMPTY(201,"用户不存在"),

    CLASS_EMPTY(300, "班级不存在"),
    REPEAT_THE_CLASS(301, "重复加入班级"),
    CLASS_PSW_ERROR(302, "班级密码错误"),
    NOT_IN_CLASS(303, "已经不在该班级"),
    CLASS_NAME_REPEAT(304, "班级名称重复"),
    CREATOR_NOT_QUIT(305, "创建人不能退出班级"),

    FILE_NULL(501, "文件为空"),
    FILE_SAVE_ERROR(502, "文件保存异常"),
    FILE_TYPE_ERROR(503, "文件类型错误"),
    FILE_NOT_EXISTS(504, "文件不存在"),

    ADMIN_EMPTY(601, "管理员用户不存在"),
    ADMIN_PASSWORD_ERROR(602, "管理员登录密码错误"),

    SUBJECT_NOT_EXIST(701,"科目不存在"),
    PAPER_NOT_EXIST(702,"试卷不存在"),
    EXAM_NOT_EXIST(703,"考试不存在"),
    EXAM_NOT_START(704,"考试未开始"),
    EXAM_IS_END(704,"考试以截止"),

    QUESTION_NOT_EXIST(801,"题目不能为空"),

    SYSTEM_EXCEPTION(-1, "系统异常"),
    REQUEST_EXCEPTION(-2, "请求异常"),
    NO_AUTH(-3, "无权限操作"),
    LOGIN_INVALID(-4, "未登录或登录过期，请重新登录"),

    MISSING_PARAMETERS(-100, "请求缺少参数"),
    METHOD_NOT_SUPPORTED(-101, "请求方法不支持"),

    QUERY_TIMEOUT(-102, "redis查询超时"),

    INVALID_CODE(40029, "code无效");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
