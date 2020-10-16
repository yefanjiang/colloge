package com.college.oss.controller;

import com.college.commonutils.UnifiedResult;
import com.college.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author winte
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public UnifiedResult uploadOssFile(MultipartFile file) {
        String url = ossService.uploadFileAvatar(file);
        return UnifiedResult.ok().data("url", url);
    }
}
