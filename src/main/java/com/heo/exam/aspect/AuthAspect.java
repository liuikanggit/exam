package com.heo.exam.aspect;

import com.heo.exam.constant.RedisConstant;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private UserRepository userRepository;

    /**
     * 验证学生
     */
    @Before("execution(public * com.heo.exam.controller.StudentController.*(..))")
    public void verifyStudent() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify(request);
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
        tokenVerify(request);
        authVerify(request, UserTypeEnum.TEACHER);
        collectionFormId(request);
    }

    @Before("execution(public * com.heo.exam.controller.UploadController.*(..))")
    public void verifyUpload(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        tokenVerify(request);
        collectionFormId(request);
    }

    private void authVerify(HttpServletRequest request, UserTypeEnum userTypeEnum) {

        String userId = (String) request.getAttribute("userId");
        if (!userRepository.existsByIdAndType(userId, userTypeEnum.getTypeCode())) {
            throw new ExamException(ResultEnum.NO_AUTH);
        }
    }

    private void tokenVerify(HttpServletRequest request) {

        /** 从请求头中获取token */
        String token = request.getHeader(RedisConstant.AUTHORIZATION);

        if (Strings.isEmpty(token)) {
            log.warn("【登录校验】token不存在");
            throw new ExamException(ResultEnum.LOGIN_INVALID);
        }
        /** 验证token是否有效 */
        if (!redisService.verify(token)) {
            throw new ExamException(ResultEnum.LOGIN_INVALID);
        }
    }

    private void collectionFormId(HttpServletRequest request) {
        /** 采集小程序的formId */
        String[] formIdList = request.getParameterValues("formId");
        if (!Objects.isNull(formIdList) && formIdList.length > 0) {
            String userId = (String) request.getAttribute("userId");
            String key = StringFormatter.format("formId_%s", userId).toString();
            log.info("formId:{} length:{}", formIdList, formIdList.length);
            for (String formId : formIdList) {
                if (Strings.isNotEmpty(formId)) {
                    /** 把formId存入以formId_+id为key的redis中 */
                    try {
                        redisTemplate.opsForList().leftPush(key, formId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
