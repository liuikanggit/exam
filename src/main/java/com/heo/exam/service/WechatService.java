package com.heo.exam.service;

import com.heo.exam.vo.ResultVO;

public interface WechatService {

    ResultVO loginOrRegister(String code, Integer type,String[] formId,String nickName,String avatarUrl,String gender);

}
