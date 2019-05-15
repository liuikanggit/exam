package com.heo.exam.aspect;

import com.heo.exam.enums.ResultEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.RedisService;
import com.sun.javafx.binding.StringFormatter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 刘康
 * @create 2019-01-31 11:16
 * @desc token认证
 **/
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRepository userRepository;

    /**
     * 验证学生
     */
    @Before("execution(public * com.heo.exam.controller.StudentController.*(..))")
    public void verifyStudent() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify();
        authVerify(request, UserTypeEnum.STUDENT);
        collectionFormId(request);
    }

    /**
     * 验证教师
     */
    @Before("execution(public * com.heo.exam.controller.TeacherController.*(..))")
    public void verifyTeacher() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify();
        authVerify(request, UserTypeEnum.TEACHER);
        collectionFormId(request);
    }

    /**
     * 上传图片也要验证token
     */
    @Before("execution(public * com.heo.exam.controller.UploadController.*(..))")
    public void verifyUpload() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify();
        collectionFormId(request);
    }

    private void authVerify(HttpServletRequest request, UserTypeEnum userTypeEnum) {

        String userId = (String) request.getAttribute("userId");
        if (!userRepository.existsByIdAndType(userId, userTypeEnum.getCode())) {
            throw new ExamException(ResultEnum.NO_AUTH);
        }
    }

    /**
     * 验证token
     */
    private void tokenVerify() {

        /** 验证token是否有效 */
        if (!redisService.verify()) {
            throw new ExamException(ResultEnum.LOGIN_INVALID);
        }
    }

    /**
     * 采集用户的formId
     *
     * @param request
     */
    private void collectionFormId(HttpServletRequest request) {
        String[] formIdList = request.getParameterValues("formId");
        if (!Objects.isNull(formIdList) && formIdList.length > 0 && !formIdList[0].equals("[]")) {
            String userId = (String) request.getAttribute("userId");
            String openId = userRepository.getOpenidById(userId);
            log.info("formId:{} length:{}", formIdList, formIdList.length);
            for (String formId : formIdList) {
                if (formId.matches("\\w{32}")) {
                    redisService.saveFormId(openId, formId);
                }
            }
        }
    }
}
