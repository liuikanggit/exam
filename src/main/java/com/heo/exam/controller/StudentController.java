package com.heo.exam.controller;

import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.ClassService;
import com.heo.exam.service.StudentService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 刘康
 * @create 2019-01-31 16:44
 * @desc 小程序学生提供接口
 **/
@RestController
@RequestMapping("/s")
public class StudentController extends BaseController{

    @Autowired
    private StudentService studentService;

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
     * @param id
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

    @PostMapping("/exit/class")
    public ResultVO quitClass(@RequestParam String classId) {
        return classService.quitClass(getUserId(),classId);
    }

    @GetMapping("/class/user")
    public ResultVO getUserListInClass(@RequestParam String classId){
        return classService.getUserListInClass(classId);
    }



}
