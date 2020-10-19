package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeVideoById(String id);

    void updateVideoInfoById(EduVideo eduVideo);


}
