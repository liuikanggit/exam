package com.heo.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.exam.config.TemplateIDConfig;
import com.heo.exam.dto.MessageParam;
import com.heo.exam.service.RedisService;
import com.heo.exam.service.WechatMessageService;
import com.heo.exam.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class WechatMessageServiceImpl implements WechatMessageService {

    @Autowired
    private TemplateIDConfig templateIDConfig;

    @Autowired
    private RedisService redisService;

    private static final String ERR_CODE = "errcode";

    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    private boolean sendMessage(String openId, MessageParam messageParam) {
        /** 根据用户的id去获取formId */
        String formId = redisService.getFormId(openId);
        log.info("formId={}", formId);
        if (formId == null) {
            log.info("信息推送失败,没有formId");
            return false;
        }
        messageParam.setFormId(formId);
        String accessToken = redisService.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        /** 解决body字符集问题 */
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + accessToken;
        String response = restTemplate.postForObject(url, messageParam.toJson(), String.class);

        Map<String, String> result = new Gson().fromJson(response, MAP_TYPE);
        int errCode = Integer.valueOf(result.get(ERR_CODE));
        if (errCode == 0) {
            log.info("信息推送成功{}", messageParam.toJson());
            return true;
        } else if (errCode == 41028 || errCode == 41029) {
            log.error("信息推送失败 formId无效,过期，换formId继续", result);
            return sendMessage(openId, messageParam);
        } else {
            log.error("信息推送失败 其他错误.{}", result);
        }
        return false;
    }

    @Override
    public boolean sendRegisterNotice(String openid, String name, String userType) {
        MessageParam messageParam = new MessageParam(openid, templateIDConfig.getRegisterNotice(), templateIDConfig.getRegisterPath());
        messageParam.addData("注册成功")
                .addData(name)
                .addData(userType)
                .addData("注册后，请尽快完善个人信息")
                .addData(DateUtil.formatter(new Date(), "yyyy-MM-dd"));
        return sendMessage(openid, messageParam);
    }

}
