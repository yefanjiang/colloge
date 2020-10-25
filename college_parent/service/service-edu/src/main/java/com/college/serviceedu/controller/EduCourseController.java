package com.college.serviceedu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.entity.vo.CourseInfoVo;
import com.college.serviceedu.entity.vo.CoursePublishVo;
import com.college.serviceedu.entity.vo.CourseQuery;
import com.college.serviceedu.service.EduCourseService;
import com.college.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("getPublishCourseInfo/{id}")
    public UnifiedResult getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return UnifiedResult.ok().data("publishCourse", coursePublishVo);
    }

    @PostMapping("publicCourse/{id}")
    public UnifiedResult publicCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return UnifiedResult.ok();
    }

    @GetMapping("getCourseList")
    public UnifiedResult getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return UnifiedResult.ok().data("list", list);
    }

    @DeleteMapping("deleteCourse/{id}")
    public UnifiedResult deleteCourse(@PathVariable String id) {
        eduCourseService.removeCourse(id);
        return UnifiedResult.ok();
    }

    @PostMapping("pageQuery/{page}/{limit}")
    public UnifiedResult pageQuery(@PathVariable long page, @PathVariable long limit,
                                   @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        //QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  UnifiedResult.ok().data("total", total).data("rows", records);

    }
}

