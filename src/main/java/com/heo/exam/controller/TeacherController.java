package com.heo.exam.controller;

import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.ClassInfoForm;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.ClassService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 刘康
 * @create 2019-02-01 13:31
 * @desc 教师控制器
 **/
@RestController
@RequestMapping("/t")
public class TeacherController extends BaseController{

    @Autowired
    private ClassService classService;

    /**
     * 查看自己的资料
     * @return
     */
    @GetMapping("/info")
    public ResultVO getUserInfo(){
        return userService.getUserInfo(getUserId());
    }

    /**
     * 查看其他用户的资料
     * @param id 用户id
     * @return
     */
    @GetMapping("/info/{id}")
    public ResultVO getUserInfo(@PathVariable String id){
        return userService.getUserInfo(id);
    }

    /**
     * 修改资料
     * @param userInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/info")
    public ResultVO updateUserInfo(@Valid UserInfoForm userInfoForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.updateUserInfo(getUserId(),userInfoForm);
    }

    /**
     * 查看班级信息
     * @param classId
     * @return
     */
    @GetMapping("/class")
    public ResultVO getClassInfo(@RequestParam String classId){
        return classService.getClassInfo(getUserId(),classId);
    }

    /**
     * 加入班级
     * @param classId
     * @return
     */
    @PostMapping("/join/class")
    public ResultVO joinClass(@RequestParam String classId){
        return classService.joinClass(getUserId(), UserTypeEnum.STUDENT,classId);
    }

    /**
     * 创建班级
     * @param classInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create/class")
    public ResultVO createClass(@Valid ClassInfoForm classInfoForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.createClass(getUserId(),classInfoForm);
    }


}
