package com.college.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.client.VodClient;
import com.college.serviceedu.entity.EduVideo;
import com.college.serviceedu.entity.vo.CoursePublishVo;
import com.college.serviceedu.mapper.EduVideoMapper;
import com.college.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Override
    public void updateVideoInfoById(EduVideo eduVideo) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(eduVideo, video);
        boolean result = this.updateById(video);
        if(!result){
            throw new MyException(20001, "课时信息保存失败");
        }
    }

    @Autowired
    public VodClient vodClient;

    @Override
    public boolean removeVideoById(String id) {
        // 根据小节ID获取视频ID，调用方法实现删除
        EduVideo eduVide = baseMapper.selectById(id);
        String videoSourceId = eduVide.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)) {
            vodClient.removeVideo(videoSourceId);
        }
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapper);

        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            String id = eduVideoList.get(i).getVideoSourceId();
            if (id != null) {
                videoSourceIdList.add(id);
            }
        }

        if (videoSourceIdList.size() != 0) {
            vodClient.removeVideoList(videoSourceIdList);
        }

        QueryWrapper<EduVideo> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.eq("course_id", courseId);
        baseMapper.delete(wrapperCourse);
    }
}
