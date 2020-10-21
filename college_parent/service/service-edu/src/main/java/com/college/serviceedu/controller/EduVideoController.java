package com.college.serviceedu.controller;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.client.VodClient;
import com.college.serviceedu.entity.EduVideo;
import com.college.serviceedu.entity.vo.CoursePublishVo;
import com.college.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.prefs.BackingStoreException;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/serviceedu/edu-video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @GetMapping("getVideo/{id}")
    public UnifiedResult getVideo(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return UnifiedResult.ok().data("video", eduVideo);
    }

    @PostMapping("addVideo")
    public UnifiedResult addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return UnifiedResult.ok();
    }

    @DeleteMapping("deleteVideo/{id}")
    public UnifiedResult deleteVideo(@PathVariable String id) {
        boolean result = eduVideoService.removeVideoById(id);
        if(result){
            return UnifiedResult.ok();
        }else{
            return UnifiedResult.error().message("删除失败");
        }
    }

    @PostMapping("updateVideo")
    public UnifiedResult updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateVideoInfoById(eduVideo);
        return UnifiedResult.ok();
    }

}

