package com.heo.exam.Scheduler;

import com.heo.exam.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 刘康
 * @create 2019-02-01 9:42
 * @desc 定时器
 **/
@Component
public class Scheduler {

    @Autowired
    private UploadService uploadService;

    @Scheduled(cron = "0 0 0 ? * 1")
    public void clearExpireImage() {
        uploadService.clearImage();
    }
}
