package com.heo.exam.service.impl;

import com.heo.exam.entity.Clazz;
import com.heo.exam.entity.User2Class;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.form.ClassInfoForm;
import com.heo.exam.repository.ClassRepository;
import com.heo.exam.repository.User2ClassRepository;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.ClassService;
import com.heo.exam.service.UploadService;
import com.heo.exam.utils.KeyUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author 刘康
 * @create 2019-02-01 14:27
 * @desc 班级service的实现
 **/
@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private User2ClassRepository user2ClassRepository;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultVO getClassInfo(String userId, String classId) {
        Clazz clazz = classRepository.getById(classId);
        if (clazz == null) {
            throw new ExamException(ResultEnum.CLASS_EMPTY);
        }
        ClassInfoVO classInfoVO = getClassInfoVo(clazz, userId);
        return ResultVOUtil.success(classInfoVO);
    }

    @Override
    public ResultVO getClassAndUserList(String userId, String classId) {
        /** 判断班级是否存在 */
        if (!classRepository.existsById(classId)) {
            throw new ExamException(ResultEnum.CLASS_EMPTY);
        }
        if (!user2ClassRepository.existsByUserIdAndClassId(userId, classId)) {
            throw new ExamException(ResultEnum.NOT_IN_CLASS);
        }
        Clazz clazz = classRepository.getById(classId);
        ClassAndUserInfo classAndUserInfo = new ClassAndUserInfo(clazz);
        classAndUserInfo.setTeacher(userRepository.findAllUserSimpleInfoByClassIdAndUserType(classId, UserTypeEnum.TEACHER.getCode()));
        classAndUserInfo.setStudent(userRepository.findAllUserSimpleInfoByClassIdAndUserType(classId, UserTypeEnum.STUDENT.getCode()));
        return ResultVOUtil.success(classAndUserInfo);
    }


    @Override
    public ResultVO joinClass(String userId, UserTypeEnum userTypeEnum, String classId, String password) {
        /** 判断班级是否存在 */
        if (!classRepository.existsById(classId)) {
            throw new ExamException(ResultEnum.CLASS_EMPTY);
        }
        /** 判断是否已经加入过 */
        if (user2ClassRepository.existsByUserIdAndClassId(userId, classId)) {
            throw new ExamException(ResultEnum.REPEAT_THE_CLASS);
        }
        /** 判断是否密码是否正确 */
        if (!classRepository.existsByIdAndPassword(classId, password)) {
            throw new ExamException(ResultEnum.CLASS_PSW_ERROR);
        }

        /** 加入班级 */
        User2Class user2Class = new User2Class(userId, userTypeEnum, classId);
        user2ClassRepository.save(user2Class);
        return ResultVOUtil.success();
    }

    @Transactional
    @Override
    public ResultVO quitClass(String userId, String classId) {
        User2Class user2Class = user2ClassRepository.findByUserIdAndClassId(userId, classId);

        /** 判断是否在班级 */
        if (user2Class == null) {
            throw new ExamException(ResultEnum.NOT_IN_CLASS); //303
        }

        /** 创建者不能退出班级 */
        if (classRepository.existsByIdAndCreatorId(classId, userId)) {
            throw new ExamException(ResultEnum.CREATOR_NOT_QUIT); //303
        }
        user2ClassRepository.delete(user2Class);

        return ResultVOUtil.success();
    }


    @Override
    @Transactional
    public ResultVO createClass(String userId, ClassInfoForm classInfoForm) {
        if (classInfoForm.getPassword() == null) {
            classInfoForm.setPassword("");
        }
        String name = classInfoForm.getName();

        if (classRepository.existsByNameAndCreatorId(name, userId)) {
            throw new ExamException(ResultEnum.CLASS_NAME_REPEAT);
        }

        /** 随机出一个不重复的classId */
        String classId = KeyUtil.getClassKey();
        while (classRepository.existsById(classId)) {
            classId = KeyUtil.getClassKey();
        }

        uploadService.saveFile(classInfoForm.getAvatar());

        /**  创建班级 */
        Clazz clazz = new Clazz();
        clazz.setId(classId);
        clazz.setCreatorId(userId);
        BeanUtils.copyProperties(classInfoForm, clazz);
        classRepository.save(clazz);

        /** 把自己加入班级 */
        User2Class user2Class = new User2Class(userId, UserTypeEnum.TEACHER, classId);
        user2ClassRepository.save(user2Class);

        /** 返回班级信息 */
        ResultVO<ClassInfoVO> resultVO = getClassInfo(userId, classId);
        resultVO.getData().setCreateTime(new Date());
        return resultVO;
    }

    @Override
    public ResultVO getAllJoinedClassInfo(String userId, int page, int size) {
        Page<ClassSimpleInfoVO> classSimpleInfoPage = user2ClassRepository.findJoinClassSimpleInfo(userId, PageRequest.of(page, size));
        return ResultVOUtil.success(new PageVo<>(classSimpleInfoPage));
    }

    @Override
    @Transactional
    public ResultVO outUser(String teacherId, String classId, String userId) {
        /** 判断班级是否存在 */
        if (!classRepository.existsById(classId)) {
            throw new ExamException(ResultEnum.CLASS_EMPTY);
        }

        /** 判断该教师是否是班级的创建人 */
        if (!classRepository.existsByIdAndCreatorId(classId, teacherId)) {
            throw new ExamException(ResultEnum.NO_AUTH);
        }

        /** 判断被删除的人是否还存在 */
        if (user2ClassRepository.existsByUserIdAndClassId(userId, classId)) {
            user2ClassRepository.deleteByClassIdAndUserId(classId, userId);
        }
        return ResultVOUtil.success();
    }

    @Override
    @Transactional
    public ResultVO disbandClass(String teacherId, String classId) {
        /** 判断班级是否存在 */
        if (!classRepository.existsById(classId)) {
            throw new ExamException(ResultEnum.CLASS_EMPTY);
        }

        /** 判断该教师是否是班级的创建人 */
        if (!classRepository.existsByIdAndCreatorId(classId, teacherId)) {
            throw new ExamException(ResultEnum.NO_AUTH);
        }
        classRepository.deleteById(classId);
        return ResultVOUtil.success();
    }

    private ClassInfoVO getClassInfoVo(Clazz clazz, String userId) {
        String classId = clazz.getId();
        ClassInfoVO classInfoVO = new ClassInfoVO(clazz);
        /** 如果该用户不是创建者，隐藏password */
        if (!userId.equals(clazz.getCreatorId())) {
            classInfoVO.hidePassword();
        }

        /** 设置创建者 */
        String creator = userRepository.getNameById(clazz.getCreatorId());
        classInfoVO.setCreator(clazz.getCreatorId());
        classInfoVO.setCreatorName(creator);


        /** 密码不为空的，表明需要密码 */
        boolean isLock = !clazz.getPassword().equals("");
        classInfoVO.setLock(isLock);

        /** 教师人数 */
        Integer teacherNumber = user2ClassRepository.countByClassIdAndUserType(classId, UserTypeEnum.TEACHER.getCode());
        classInfoVO.setTeacherNumber(teacherNumber);

        /** 学生人数 */
        Integer studentNumber = user2ClassRepository.countByClassIdAndUserType(classId, UserTypeEnum.STUDENT.getCode());
        classInfoVO.setStudentNumber(studentNumber);

        classInfoVO.setInClass(user2ClassRepository.existsByUserIdAndClassId(userId,classId));

        return classInfoVO;
    }

}
