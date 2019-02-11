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
import com.heo.exam.utils.KeyUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ClassInfoVO;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResultVO getClassInfo(String userId,String classId) {
        Clazz clazz = classRepository.findById(classId).get();
        if (clazz == null) {
            throw new ExamException(ResultEnum.CLASS_NOT_EMPTY);
        }
        ClassInfoVO classInfoVO = new ClassInfoVO();
        BeanUtils.copyProperties(clazz, classInfoVO);

        /** 如果该用户不是创建者，隐藏password */
        if(!userId.equals(clazz.getCreateId())){
            classInfoVO.hidePassword();
        }

        /** 设置创建者 */
        String creator = userRepository.getNameById(clazz.getCreateId());
        classInfoVO.setCreator(creator);

        /** 密码不为空的，表明需要密码 */
        boolean isLock = !clazz.getPassword().equals("");
        if (isLock){
            classInfoVO.setLock(isLock);
        }


        /** 教师人数 */
        Integer teacherNumber = user2ClassRepository.countByClassIdAndUserType(classId, UserTypeEnum.TEACHER.getTypeCode());
        classInfoVO.setTeacherNumber(teacherNumber);

        /** 学生人数 */
        Integer studentNumber = user2ClassRepository.countByClassIdAndUserType(classId, UserTypeEnum.STUDENT.getTypeCode());
        classInfoVO.setStudentNumber(studentNumber);

        return ResultVOUtil.success(classInfoVO);
    }

    @Override
    public ResultVO getUserListInClass(String classId) {
        return null;
    }


    @Override
    public ResultVO joinClass(String userId, UserTypeEnum userTypeEnum, String classId) {
        /** 判断班级是否存在 */
        if (!classRepository.existsById(classId)) {
            throw new ExamException(ResultEnum.CLASS_NOT_EMPTY);
        }
        /** 判断是否已经加入过 */
        if (user2ClassRepository.existsByUserIdAndClassId(userId, classId)) {
            throw new ExamException(ResultEnum.REPEAT_THE_CLASS);
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

        user2ClassRepository.delete(user2Class);

        return ResultVOUtil.success();
    }


    @Override
    @Transactional
    public ResultVO createClass(String userId, ClassInfoForm classInfoForm) {
        /** 随机出一个不重复的classId */
        String classId = KeyUtil.getClassKey();
        while (classRepository.existsById(classId)) {
            classId = KeyUtil.getClassKey();
        }
        /**  创建班级 */
        Clazz clazz = new Clazz();
        clazz.setId(classId);
        clazz.setCreateId(userId);
        BeanUtils.copyProperties(classInfoForm, clazz);
        classRepository.save(clazz);

        /** 把自己加入班级 */
        User2Class user2Class = new User2Class(userId, UserTypeEnum.TEACHER, classId);
        user2ClassRepository.save(user2Class);
        /** 返回班级信息 */
        ResultVO<ClassInfoVO> resultVO = getClassInfo(userId,classId);
        resultVO.getData().setCreateTime(new Date());
        int a = 1/0;
        return resultVO;
    }

}
