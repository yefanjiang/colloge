package com.college.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.extension.api.R;
import com.college.commonutils.UnifiedResult;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.vod.service.VodService;
import com.college.vod.utils.ConstantPropertiesUtil;
import com.college.vod.utils.InitObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @DeleteMapping("removeVideoList")
    public UnifiedResult removeVideoList(@RequestParam("videoList") List<String> videoIdList) {
        vodService.removeVideoList(videoIdList);
        return UnifiedResult.ok().message("删除成功");
    }

    @GetMapping("getPlayAuth/{id}")
    public UnifiedResult getPlayAuth(@PathVariable String id) {

        try  {
            DefaultAcsClient client = InitObject.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(id);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return UnifiedResult.ok().message("获取凭证成功").data("playAuth", playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001, "获取凭证失败");
        }

    }

}
