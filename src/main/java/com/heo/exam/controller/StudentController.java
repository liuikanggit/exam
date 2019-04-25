package com.heo.exam.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.Answer;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.ClassService;
import com.heo.exam.service.ExamService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private ExamService examService;

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
     * 加入班级
     *
     * @param classId
     * @return
     */
    @PostMapping("/join/class")
    public ResultVO joinClass(@RequestParam String classId,@RequestParam(required = false,defaultValue = "") String password) {
        return classService.joinClass(getUserId(), UserTypeEnum.STUDENT, classId,password);
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
     * 给用户点赞
     * @param userId
     * @return
     */
    @PostMapping("/like")
    public ResultVO likeUser(@RequestParam String userId) {
        return userService.like(getUserId(), userId);
    }

    /**
     * 获取自己等待的考试
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/exam")
    public ResultVO getExam(@RequestParam(required = false,defaultValue = "false") boolean end,
                            @RequestParam(required = false,defaultValue = "0") Integer page,
                            @RequestParam(required = false,defaultValue = "50") Integer size){
        if (end){
            return examService.getEndExamSimpleInfoByStudent(getUserId(),page,size);
        }
        return examService.getExamSimpleInfoByStudent(getUserId(),page,size);
    }

    /**
     * 获取考试的详细信息
     * @param id
     * @return
     */
    @GetMapping("/exam/{id}")
    public ResultVO getExamDetail(@PathVariable Integer id){
        return examService.getExamDetailInfoByStudent(getUserId(),id);
    }

    /**
     * 开始考试或继续考试
     * @param id
     * @return
     */
    @GetMapping("/start/exam/{id}")
    public ResultVO startExam(@PathVariable Integer id){
        return examService.startExam(getUserId(),id);
    }

    @PostMapping("/save/exam")
    public ResultVO saveExam(@RequestParam Integer id, @RequestParam String answer){
        Map<String,String> answerMap = new Gson().fromJson(answer,new TypeToken<Map<Integer,String>>(){}.getType() );
        return examService.saveExam(getUserId(),id,answerMap);
    }

    @PostMapping("/submit/exam")
    public ResultVO submitExam(@RequestParam Integer id){
        return examService.submitExam(getUserId(),id);
    }

}
