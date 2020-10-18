package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.vo.CourseInfoVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);
}
