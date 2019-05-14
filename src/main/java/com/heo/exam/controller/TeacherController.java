package com.heo.exam.controller;

import com.heo.exam.enums.GradeEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.ClassInfoForm;
import com.heo.exam.form.UserInfoForm;
import com.heo.exam.service.ClassService;
import com.heo.exam.service.ExamService;
import com.heo.exam.service.PaperService;
import com.heo.exam.service.SubjectService;
import com.heo.exam.utils.DateUtil;
import com.heo.exam.utils.EnumUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    @Autowired
    private ExamService examService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private SubjectService subjectService;


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


    /**
     * 创建考试
     *
     * @param paperId   试卷id
     * @param classId   班级id（可多选）
     * @param name      考试名称
     * @param desc      描述
     * @param startDate 考试时间
     * @param endDate   截止时间
     * @param time      考试时长（分钟）
     * @return
     * @throws ParseException
     */
    @PostMapping("/exam")
    public ResultVO createExam(@RequestParam Integer paperId,
                               @RequestParam String[] classId,
                               @RequestParam String name,
                               @RequestParam String desc,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               @RequestParam Integer time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return examService.createExam(getUserId(), paperId, classId, name, desc, sdf.parse(startDate), sdf.parse(endDate), time);
    }

    /**
     * 获取考试列表（分页）
     * @param page 页数
     * @param size 数量
     * @return
     */
    @GetMapping("/exam")
    public ResultVO getExam(@RequestParam Integer page,@RequestParam Integer size){
        return examService.getExamSimpleInfoByTeacher(getUserId(),page,size);
    }

    /**
     * 创建试卷
     *
     * @param subject  科目id
     * @param grade    年级id
     * @param name     名称
     * @param score    分数
     * @param desc     描述
     * @param question 题目（多选）
     * @return
     */
    @PostMapping("/paper")
    public ResultVO createPaper(@RequestParam Integer subject,
                                @RequestParam Integer grade,
                                @RequestParam String name,
                                @RequestParam Integer score,
                                @RequestParam String desc,
                                @RequestParam Integer[] question) {
        return paperService.savePaper(null, getUserId(), subject, grade, name, score, desc, question);
    }

    /**
     * 获取试卷（分页）
     * @param self 是否查看自己创建的
     * @param subject 科目分类
     * @param grade 年级分类
     * @param page 页数
     * @param size 数量
     * @return
     */
    @GetMapping("/paper")
    public ResultVO getPaper(@RequestParam(required = false, defaultValue = "false") boolean self,
                             @RequestParam(required = false) Integer subject,
                             @RequestParam(required = false) Integer grade,
                             @RequestParam Integer page, @RequestParam Integer size) {
        String teacherId = null;
        if (self) {
            teacherId = getUserId();
        }
        return paperService.getAllPaper(teacherId, subject, grade, page, size);
    }



    /**
     * 获取科目
     */
    @GetMapping("/subject")
    public ResultVO getSubject() {
        return subjectService.getAllSubject();
    }

}
