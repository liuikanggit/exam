package com.heo.exam.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 刘康
 * @create 2019-02-01 9:23
 * @desc 上传文件的配置信息
 **/
@Data
@Component
@ConfigurationProperties(prefix = "upload")
public class UploadConfig {

    private String tempPath;

    private String savePath;

    private int expireTime; //图片过期时间 单位天
}
