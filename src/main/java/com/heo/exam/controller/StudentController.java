package com.heo.exam.controller;

import com.heo.exam.enums.UserTypeEnum;
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
 * @create 2019-01-31 16:44
 * @desc 小程序学生提供接口
 **/
@RestController
@RequestMapping("/s")
public class StudentController extends BaseController {

    @Autowired
    private ClassService classService;

    /**
     * 查看自己的资料
     *
     * @return
     */
    @GetMapping("/info")
    public ResultVO getUserInfo() {
        return userService.getUserInfo(getUserId());
    }

    /**
     * 查看其他用户的资料
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public ResultVO getUserInfo(@PathVariable String id) {
        return userService.getUserInfo(id);
    }

    /**
     * 修改资料
     *
     * @param userInfoForm
     * @param bindingResult
     * @return
     */
    @PutMapping("/info")
    public ResultVO updateUserInfo(@Valid UserInfoForm userInfoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return userService.updateUserInfo(getUserId(), userInfoForm);
    }

    /**
     * 搜索班级
     *
     * @param classId
     * @return
     */
    @GetMapping("/class/{classId}")
    public ResultVO getClassInfo(@PathVariable String classId) {
        return classService.getClassInfo(getUserId(), classId);
    }


    /**
     * 获取自己加入的所有班级信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/class")
    public ResultVO getAllClass(@RequestParam(required = false,defaultValue = "0") int page,
                                @RequestParam(required = false,defaultValue = "50") int size) {
        return classService.getAllJoinedClassInfo(getUserId(), page, size);
    }

    /**
     * 加入班级
     *
     * @param classId
     * @return
     */
    @PostMapping("/join/class")
    public ResultVO joinClass(@RequestParam String classId) {
        return classService.joinClass(getUserId(), UserTypeEnum.STUDENT, classId);
    }

    /**
     * 退出班级
     *
     * @param classId
     * @return
     */
    @DeleteMapping("/exit/class")
    public ResultVO quitClass(@RequestParam String classId) {
        return classService.quitClass(getUserId(), classId);
    }

    /**
     * 获取班级中所有同学
     *
     * @param classId
     * @return
     */
    @GetMapping("/class/user")
    public ResultVO getUserListInClass(@RequestParam String classId) {
        return classService.getUserListInClass(getUserId(),classId);
    }


}
