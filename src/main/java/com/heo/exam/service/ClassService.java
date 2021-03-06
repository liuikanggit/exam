package com.heo.exam.service;

import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.form.ClassInfoForm;
import com.heo.exam.vo.ResultVO;

import javax.validation.Valid;

/**
 * @author 刘康
 * @create 2019-02-01 14:26
 * @desc 班级service
 **/
public interface ClassService {

    /**
     * 查看班级信息
     *
     * @param userId  用户id 用来判断是否是创建者
     * @param classId 班级id
     * @return 班级信息
     */
    ResultVO getClassInfo(String userId, String classId);

    /**
     * 查看班级里的所有用户(学生和老师)
     *
     * @param classId
     * @return
     */
    ResultVO getClassAndUserList(String userId,String classId);

    /**
     * 加入班级
     *
     * @param userId  用户id
     * @param classId 班级id
     * @return 成功or失败
     */
    ResultVO joinClass(String userId, UserTypeEnum userTypeEnum, String classId,String password);

    /**
     * 退出班级
     *
     * @param userId
     * @param classId
     * @return
     */
    ResultVO quitClass(String userId, String classId);

    /**
     * 创建班级
     *
     * @param userId        创建者id
     * @param classInfoForm 班级信息
     * @return
     */
    ResultVO createClass(String userId, ClassInfoForm classInfoForm);

    /**
     * 获取自己加入的班级简要信息 分页
     * @param userId
     * @param page
     * @param size
     * @return
     */
    ResultVO getAllJoinedClassInfo(String userId, int page, int size);

    /**
     * 踢出用户
     * @param userId 教师iid
     * @param classId 班级id
     * @param userId1 用户id
     * @return
     */
    ResultVO outUser(String userId, String classId, String userId1);

    /**
     * 解散班级
     * @param teacherId 教师id
     * @param classId 班级id
     * @return
     */
    ResultVO disbandClass(String teacherId, String classId);
}
