package com.college.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.entity.EduVideo;
import com.college.serviceedu.entity.vo.CoursePublishVo;
import com.college.serviceedu.mapper.EduVideoMapper;
import com.college.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean removeVideoById(String id) {
        Integer result = baseMapper.deleteById(id);
        return null != result && result > 0;
    }

    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
