package com.heo.exam.service.impl;

import com.heo.exam.config.UploadConfig;
import com.heo.exam.constant.FileType;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.service.UploadService;
import com.heo.exam.utils.KeyUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-02-01 9:03
 * @desc 上传文件服务的实现类
 **/
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Autowired
    private UploadConfig uploadConfig;

    @Override
    public ResultVO uploadFile(MultipartFile file, int fileType) {
        if (file.isEmpty()) {
            throw new ExamException(ResultEnum.FILE_NULL);
        }

        String filename = file.getOriginalFilename();
        String suffixName = filename.substring(filename.lastIndexOf(".") + 1);

        switch (fileType) {
            case FileType.IMAGE:
                /** gif  jpg  bmp jpeg png */
                if (!suffixName.matches("[Gg][Ii][Ff]|[Jj][Pp][Gg]|[Bb][Mm][Pp]|[Jj][Pp][Ee][Gg]|[Pp][Nn][Gg]")) {
                    throw new ExamException(ResultEnum.FILE_TYPE_ERROR);
                }
                break;
            default:
                if (Strings.isEmpty(suffixName)) {
                    throw new ExamException(ResultEnum.FILE_TYPE_ERROR);
                }
                break;
        }

        /**生成图片名称*/
        String newFilename = KeyUtil.getUUID() + "." + suffixName;

        try {
            Files.copy(file.getInputStream(), Paths.get(uploadConfig.getTempPath(), newFilename));
        } catch (IOException e) {
            throw new ExamException(ResultEnum.FILE_SAVE_ERROR);
        }

        return ResultVOUtil.success(newFilename);
    }

    @Override
    public boolean saveFile(String imageName) {
        if (Strings.isNotEmpty(imageName) && !imageName.matches("^http.*")) {
            Path path = Paths.get(uploadConfig.getTempPath(), imageName);
            if (Files.exists(path)) {
                try {
                    Files.move(path, Paths.get(uploadConfig.getSavePath(), imageName), StandardCopyOption.ATOMIC_MOVE);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ExamException(ResultEnum.FILE_SAVE_ERROR);
                }
            } else if (Files.exists(Paths.get(uploadConfig.getSavePath(), imageName))) {
                /** 文件已经保存过了 */
                return false;
            } else {
                log.error("图片不存在:{}", uploadConfig.getTempPath() + "/" + imageName);
                throw new ExamException(ResultEnum.FILE_NOT_EXISTS);
            }
        } else {
            return false;
        }
    }

    @Override
    public void clearImage() {
        int imageNum = 0;
        int expireNum = 0;
        log.info("正在清除过期图片", imageNum, expireNum);

        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(uploadConfig.getTempPath()));
            for (Path file : stream) {
                imageNum++;
                Long fileCreateTime = Files.readAttributes(file, BasicFileAttributes.class).creationTime().toMillis();
                Long nowTime = new Date().getTime();
                if (fileCreateTime < nowTime - uploadConfig.getExpireTime() * 24 * 60 * 60 * 1000) {
                    expireNum++;
                    log.info("{} 图片已经过期，以清除!", file.getFileName());
                    Files.delete(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("清除过期图片以完成，一共{}张图片，过期{}张", imageNum, expireNum);
    }
}
