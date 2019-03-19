package com.heo.exam.exception;

import com.heo.exam.enums.ExcelInputEnum;
import lombok.Getter;

/**
 * @author 刘康
 * @create 2019-02-22 10:33
 * @desc excel录入信息的异常
 **/
@Getter
public class ExcelException extends Exception{

    private Integer code;

    public ExcelException( ExcelInputEnum excelInputEnum){
        super(excelInputEnum.getMessage());
        this.code = excelInputEnum.getCode();
    }

}
