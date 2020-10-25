package com.college.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.service.EduCourseService;
import com.college.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author winte
 */
@RestController
@CrossOrigin
@RequestMapping("/serviceedu/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public UnifiedResult getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduTeacherService.getTeacherFrontList(eduTeacherPage);

        return UnifiedResult.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public UnifiedResult getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher eduTeacher = eduTeacherService.getById(teacherId);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseList = eduCourseService.list(wrapper);

        return UnifiedResult.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
