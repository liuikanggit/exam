package com.heo.exam.handler;

import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author 刘康
 * @create 2019-01-31 10:50
 * @desc 异常捕获类
 **/
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultVO handle(Exception e){
        if(e instanceof ExamException) {
            ExamException examException = (ExamException) e;
            return ResultVOUtil.error(examException.getCode(), examException.getMessage());
        }
        else if(e instanceof MissingServletRequestParameterException){
            return ResultVOUtil.error(ResultEnum.MISSING_PARAMETERS,e.getMessage());
        }
        else if(e instanceof MissingServletRequestPartException){
            return ResultVOUtil.error(ResultEnum.MISSING_PARAMETERS,e.getMessage());
        }
        else if(e instanceof HttpRequestMethodNotSupportedException){
            return ResultVOUtil.error(ResultEnum.METHOD_NOT_SUPPORTED,e.getMessage());
        }
        else if(e instanceof QueryTimeoutException){
            log.error("redis又超时啦");
            return ResultVOUtil.error(ResultEnum.QUERY_TIMEOUT,e.getMessage());
        }
        else {
            log.error("[系统错误{}]", e);
            return ResultVOUtil.error(-1, "未知错误");
        }
    }
}
