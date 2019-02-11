package com.heo.exam.controller;

import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 刘康
 * @create 2019-01-31 11:23
 * @desc 测试控制器
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public ResultVO test(){
        return ResultVOUtil.success();
    }

}
