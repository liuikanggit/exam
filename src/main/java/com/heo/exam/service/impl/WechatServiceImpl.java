package com.heo.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.heo.exam.config.WechatAccountConfig;
import com.heo.exam.entity.User;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.enums.UserTypeEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.repository.UserRepository;
import com.heo.exam.service.RedisService;
import com.heo.exam.service.WechatService;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RedisService redisService;

    @Value("${backDoorCode}")
    private String backDoorCode;

    /**
     * 小程序端的登录
     * @param code
     * @param type
     * @return
     */
    @Override
    public ResultVO loginOrRegister(String code, Integer type) {
        /** 检查type参数是否合法 */
        if( !UserTypeEnum.contains(type)){
            throw new ExamException(ResultEnum.PARAM_ERROR);
        }
        String openid;
        /** Back door code */
        if (code.equals(backDoorCode)){
            openid = "test_openid";
        }else{
            /** 用code去换openid */
            openid = auth(code);
        }

        /** 判断用户是否第一次登录 */
        String userId = userRepository.getIdByOpenidAndType(openid,type);
        if(userId == null){
            /** 注册用户 */
            User user = new User(openid,type);
            user = userRepository.save(user);
            userId = user.getId();
            /** 推送模板信息，提示用户注册成功 */

        }
        String token = redisService.login(userId);

        return ResultVOUtil.success(token);
    }


    /**
     * code换openid
     * @param code
     * @return openid
     */
    private String auth(String code) {
        /** 用code换取用户openid */
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + wechatAccountConfig.getAppId()
                +"&secret="+
                wechatAccountConfig.getSecret()
                +"&js_code="
                + code
                + "&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url,String.class);
        Map<String,String> resultMap =  new Gson().fromJson(result,new TypeToken<Map<String, String>>() {}.getType());
        if (resultMap.get("openid") == null){
            throw new ExamException(ResultEnum.INVALID_CODE);
        }
        /** 返回拿到的openid */
        return resultMap.get("openid");
    }


}
