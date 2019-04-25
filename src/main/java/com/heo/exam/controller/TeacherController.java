package com.heo.exam.controller;

import com.heo.exam.enums.GradeEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.ClassInfoForm;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.ClassService;
import com.heo.exam.utils.EnumUtil;
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
public class TeacherController extends BaseController {

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
     * @param id 用户id
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
     * 创建班级
     *
     * @param classInfoForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/class")
    public ResultVO createClass(@Valid ClassInfoForm classInfoForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1, bindingResult.getFieldError().getDefaultMessage());
        }
        return classService.createClass(getUserId(), classInfoForm);
    }

    /**
     * 获取自己加入的所有班级信息
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/class")
    public ResultVO getAllClass(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "50") int size) {
        return classService.getAllJoinedClassInfo(getUserId(), page, size);
    }

    /**
     * 搜索班级信息
     *
     * @param classId
     * @return
     */
    @GetMapping("/class/{classId}")
    public ResultVO getClassInfo(@PathVariable String classId) {
        return classService.getClassInfo(getUserId(), classId);
    }

    /**
     * 加入班级
     *
     * @param classId
     * @return
     */
    @PostMapping("/join/class")
    public ResultVO joinClass(@RequestParam String classId, @RequestParam(required = false, defaultValue = "") String password) {
        return classService.joinClass(getUserId(), UserTypeEnum.TEACHER, classId, password);
    }

    /**
     * 获取班级信息和成员
     *
     * @param classId
     * @return
     */
    @GetMapping("/class/user")
    public ResultVO getUserListInClass(@RequestParam String classId) {
        return classService.getClassAndUserList(getUserId(), classId);
    }

    /**
     * 踢出班级成员
     *
     * @param classId
     * @param userId
     * @return
     */
    @DeleteMapping("/class/user")
    public ResultVO outUserInClass(@RequestParam String classId, @RequestParam String userId) {
        return classService.outUser(getUserId(), classId, userId);
    }

    /**
     * 退出班级
     *
     * @param classId
     * @return
     */
    @DeleteMapping("/exit/class")
    public ResultVO exitClass(@RequestParam String classId) {
        return classService.quitClass(getUserId(), classId);
    }

    /**
     * 解散班级
     *
     * @param classId
     * @return
     */
    @DeleteMapping("/class")
    public ResultVO disbandClass(@RequestParam String classId) {
        return classService.disbandClass(getUserId(), classId);
    }

    /**
     * 点赞
     */
    @PostMapping("/like")
    public ResultVO likeUser(@RequestParam String userId) {
        return userService.like(getUserId(), userId);
    }

    /**
     * 获取年级
     *
     * @return
     */
    @GetMapping("/grade")
    public ResultVO getGrade() {
        return ResultVOUtil.success(EnumUtil.getNameList(GradeEnum.class));
    }

}
