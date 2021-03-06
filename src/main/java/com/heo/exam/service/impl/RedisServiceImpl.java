package com.heo.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.exam.config.WechatAccountConfig;
import com.heo.exam.constant.RedisConstant;
import com.heo.exam.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘康
 * @create 2019-01-31 15:52
 * @desc 登录service的实现
 **/
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public String login(String userId) {
        /** 判断用户的token是否已经存在了 */
        String token = redisTemplate.opsForValue().get(userId);

        if (token != null) {
            return token;
        }
        token = UUID.randomUUID().toString();
        // 保存token
        saveToken(userId, token);
        return token;
    }

    /**
     * 退出登录
     */
    @Override
    public void logout(String userId) {
        if (userId==null){
            return;
        }
        String token = redisTemplate.opsForValue().get(userId);
        if (token != null) {
            /** 移除token */
            redisTemplate.opsForValue().getOperations().delete(userId);
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, token));
        }
    }


    /**
     * 验证token是否有效 并且 刷新token有效期
     *
     * @return
     */
    @Override
    public boolean verify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        /** 从请求头中获取token */
        String token = request.getHeader(RedisConstant.AUTHORIZATION);
        // 使用token 获取userId
        String userId = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, token));
        if (Strings.isEmpty(userId)) {
            return false;
        }

        request.setAttribute("userId", userId);
        // 刷新token时间有效期
        saveToken(userId, token);
        return true;
    }

    private void saveToken(String userId, String token) {
        /** 把用户id  作为key，token作为value */
        redisTemplate.opsForValue().set(userId, token, RedisConstant.EXPIRE, TimeUnit.SECONDS);

        /** 再把token_{token}作为key，用户id 作为value */
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), userId, RedisConstant.EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    public void saveFormId(String openId, String formId) {
        if (Strings.isNotEmpty(formId)) {
            /** 把formId_openId为key， fromId为value */
            redisTemplate.opsForList().leftPush(String.format(RedisConstant.FORM_ID_PREFIX, openId), formId);
        }
    }

    @Override
    public String getFormId(String openId) {
        return redisTemplate.opsForList().rightPop(String.format(RedisConstant.FORM_ID_PREFIX, openId));
    }

    private static final String EXPIRES_IN = "expires_in";
    private static final String ERR_CODE = "errcode";
    private static final String ERR_MSG = "errmsg";

    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    @Override
    public String getAccessToken() {
        String accessToken = redisTemplate.opsForValue().get(RedisConstant.ACCESS_TOKEN);

        if (Objects.isNull(accessToken) || Strings.isEmpty(accessToken)) {
            RestTemplate restTemplate = new RestTemplate();
            final String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" +
                    wechatAccountConfig.getAppId() +
                    "&secret=" +
                    wechatAccountConfig.getSecret();
            String response = restTemplate.getForObject(url, String.class);
            Map<String, String> result = new Gson().fromJson(response, MAP_TYPE);
            if (result.get(ERR_CODE) != null) {
                log.error("获取accessToken失败,err_code:{},err_msg:{}", result.get(ERR_CODE), result.get(ERR_MSG));
            } else {
                accessToken = result.get(RedisConstant.ACCESS_TOKEN);
                int expiresIn = Integer.valueOf(result.get(EXPIRES_IN)) - 60; //默认是7200秒 2小时
                /** 缓存到redis */
                redisTemplate.opsForValue().set(RedisConstant.ACCESS_TOKEN, accessToken, expiresIn, TimeUnit.SECONDS);
            }
        }
        return accessToken;
    }

}
