package com.heo.exam.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-22 15:23
 * @desc 批量操作的时候，返回的结果
 **/
@Data
public class BatchVO {

    private Integer cmdNumber;

    private Integer success;

    private Integer error;

    private List<ErrorDetail> errorDetailList;

    public BatchVO(){
        this.cmdNumber = 0;
        this.success = 0;
        this.error = 0;
    }

}
