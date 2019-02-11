package com.heo.exam.vo;

import lombok.Data;

/**
 * @author 刘康
 * @create 2019-01-31 10:38
 * @desc 返回数据的包装
 *
 **/
@Data
public class ResultVO<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体内容. */
    private T data;
}
