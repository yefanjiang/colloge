package com.college.serviceedu.controller;


import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.vo.CourseInfoVo;
import com.college.serviceedu.service.EduCourseService;
import com.college.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/serviceedu/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    public UnifiedResult addCourseInfo(@RequestBody(required = true) CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return UnifiedResult.ok().data("courseId", id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public UnifiedResult getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return UnifiedResult.ok().data("courseInfoVo", courseInfoVo);
    }

    @PostMapping("updateCourseInfo")
    public UnifiedResult updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return UnifiedResult.ok();
    }


}

