package com.heo.exam.vo;

import com.heo.exam.enums.ExcelInputEnum;
import com.heo.exam.enums.ResultEnum;
import lombok.Data;

/**
 * @author 刘康
 * @create 2019-02-22 10:12
 * @desc 错误细节实体类
 **/
@Data
public class ErrorDetail {
    /** 错误代码 */
    private Integer code;

    /** 错误位置 */
    private Integer index;

    /** 错误文本说明 */
    private String message;

    public ErrorDetail(){
    }

    public ErrorDetail(Integer index, Integer code,String message){
        this.index = index;
        this.code = code;
        this.message = message;
    }

    public ErrorDetail(Integer index, ExcelInputEnum excelInputEnum){
        this.index = index;
        this.code = excelInputEnum.getCode();
        this.message = excelInputEnum.getMessage();
    }
}
