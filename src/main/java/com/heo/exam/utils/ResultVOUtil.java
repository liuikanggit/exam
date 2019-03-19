package com.heo.exam.utils;

import com.heo.exam.enums.ResultEnum;
import com.heo.exam.vo.ErrorDetail;
import com.heo.exam.vo.ResultVO;

/**
 * @author 刘康
 * @create 2019-01-31 10:44
 * @desc result的根据类
 **/
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        return error(code,msg,null);
    }

    public static ResultVO error(Integer code, String msg, Object errorDetail) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        resultVO.setData(errorDetail);
        return resultVO;
    }

    public static ResultVO error(ResultEnum em) {
        return error(em,null);
    }


    public static ResultVO error(ResultEnum em,String error) {
        ResultVO resultVO = error(em.getCode(),em.getMessage());
        resultVO.setData(error);
        return resultVO;
    }
}
