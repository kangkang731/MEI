package com.mei.web.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.mei.common.config.RuoYiConfig;
import com.mei.common.core.domain.AjaxResult;
import com.mei.common.utils.file.FileUploadUtils;
import com.mei.common.utils.file.FileUtils;
import com.mei.framework.config.ServerConfig;

/**
 * 文件上传控制器
 * 供MEI-UI前端调用（通过Vite代理 /api -> /）
 */
@RestController
public class UploadController {

    private static final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private ServerConfig serverConfig;

    /**
     * 通用文件上传
     * POST /api/upload -> /upload
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        return doUpload(file);
    }

    /**
     * 文件上传（带file路径）
     * POST /api/file/upload -> /file/upload
     */
    @PostMapping("/file/upload")
    public AjaxResult fileUpload(MultipartFile file) throws Exception {
        return doUpload(file);
    }

    /**
     * 头像上传
     * POST /api/upload/avatar -> /upload/avatar
     */
    @PostMapping("/upload/avatar")
    public AjaxResult uploadAvatar(MultipartFile file) throws Exception {
        return doUpload(file);
    }

    /**
     * 简历上传
     * POST /api/upload/resume -> /upload/resume
     */
    @PostMapping("/upload/resume")
    public AjaxResult uploadResume(MultipartFile file) throws Exception {
        return doUpload(file);
    }

    private AjaxResult doUpload(MultipartFile file) throws Exception {
        try {
            String filePath = RuoYiConfig.getUploadPath();
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            // 兼容前端 fileId 引用
            ajax.put("fileId", fileName);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
