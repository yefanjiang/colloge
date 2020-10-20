package com.college.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.college.commonutils.UnifiedResult;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.vod.service.VodService;
import com.college.vod.utils.ConstantPropertiesUtil;
import com.college.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    public UnifiedResult uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideoAly(file);
        return UnifiedResult.ok().data("videoId", videoId);
    }

    @DeleteMapping("removeVideo/{id}")
    public UnifiedResult removeVideo(@PathVariable String id) {
        try {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);
            client.getAcsResponse(request);
            return UnifiedResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "删除视频失败");
        }
    }

}
