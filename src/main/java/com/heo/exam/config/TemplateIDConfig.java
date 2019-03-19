package com.heo.exam.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "template")
public class TemplateIDConfig {

    private String registerNotice;

    private String registerPath;

}
