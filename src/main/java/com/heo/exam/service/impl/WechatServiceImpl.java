package com.heo.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.exam.config.WechatAccountConfig;
import com.heo.exam.entity.User;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.enums.SexEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.RedisService;
import com.heo.exam.service.WechatMessageService;
import com.heo.exam.service.WechatService;
import com.heo.exam.utils.EnumUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Autowired
    private WechatMessageService wechatMessageService;


    /**
     * 小程序端的登录
     *
     * @param code
     * @param type
     * @return
     */
    @Override
    public ResultVO loginOrRegister(String code, Integer type, String[] formId, String nickName, String avatarUrl, String gender) {
        /** 检查type参数是否合法 */
        if (!EnumUtil.contains(UserTypeEnum.class, type)) {
            throw new ExamException(ResultEnum.PARAM_ERROR);
        }

        /** 用code去换openid */
        String openid = auth(code);

        /** 判断用户是否第一次登录 */
        String userId = userRepository.getIdByOpenidAndType(openid, type);
        boolean f = false;
        if (userId == null) {
            f = true;
            /** 注册用户 */
            User user = new User(openid, type);
            user.setName(nickName);
            user.setAvatar(avatarUrl);
            //性别 0：未知、1：男、2：女
            if ("0".equals(gender)) {
                user.setSex(SexEnum.UNKNOWN.getCode());
            } else if ("1".equals(gender)) {
                user.setSex(SexEnum.MALE.getCode());
            } else if ("2".equals("女")) {
                user.setSex(SexEnum.FEMALE.getCode());
            }
            user = userRepository.save(user);
            userId = user.getId();
            log.info("有新的用户注册成功了,{}", user);
        }

        /** 保存用户formid */
        saveFormId(userId, formId);

        if (f) {
            /**  推送模板信息 */
            wechatMessageService.sendRegisterNotice(userId, openid, nickName, EnumUtil.getName(UserTypeEnum.class, type));
        }

        String token = redisService.login(userId);

        return ResultVOUtil.success(token);
    }

    private void saveFormId(String userId, String[] formId) {
        if (formId != null) {
            for (String aFormId : formId) {
                redisService.saveFormId(userId, aFormId);
            }
        }
    }


    /**
     * code换openid
     *
     * @param code
     * @return openid
     */
    private String auth(String code) {
        /** 用code换取用户openid */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + wechatAccountConfig.getAppId()
                + "&secret=" +
                wechatAccountConfig.getSecret()
                + "&js_code="
                + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        Map<String, String> resultMap = new Gson().fromJson(result, new TypeToken<Map<String, String>>() {
        }.getType());
        if (resultMap.get("openid") == null) {
            throw new ExamException(ResultEnum.INVALID_CODE);
        }
        /** 返回拿到的openid */
        return resultMap.get("openid");
    }


}
