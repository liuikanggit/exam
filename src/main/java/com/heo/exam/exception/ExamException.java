package com.heo.exam.exception;

import com.heo.exam.enums.ResultEnum;
import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-01-31 10:41
 * @desc 异常类
 **/
@Getter
public class ExamException extends RuntimeException{
    private Integer code;

    public ExamException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public ExamException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
