package com.heo.exam.service;

import com.heo.exam.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 刘康
 * @create 2019-02-01 9:03
 * @desc 上传文件的service
 **/
public interface UploadService {

    /** 上传文件到临时目录 */
    ResultVO uploadFile(MultipartFile file,int fileType);

    /** 临时文件保存到正式目录中 */
    boolean saveFile(String file);

    /** 清除临时文件 */
    void clearImage();

}
