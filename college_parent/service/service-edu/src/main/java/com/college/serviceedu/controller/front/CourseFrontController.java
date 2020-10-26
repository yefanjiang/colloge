package com.college.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.entity.frontvo.CourseFrontVo;
import com.college.serviceedu.entity.frontvo.CourseWebVo;
import com.college.serviceedu.entity.vo.ChapterVo;
import com.college.serviceedu.service.EduChapterService;
import com.college.serviceedu.service.EduCourseService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceedu/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("getFrontCourseList/{page}/{limit}")
    public UnifiedResult getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                            @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> eduTeacherPage = new Page<>(page, limit);
        Map<String, Object> map = eduCourseService.getCourseFrontList(eduTeacherPage, courseFrontVo);

        return UnifiedResult.ok().data(map);
    }

    @Autowired
    private EduChapterService eduChapterService;

    @GetMapping("getFrontCourseInfo/{courseId}")
    public UnifiedResult getFrontCourseInfo(@PathVariable String courseId) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        // 之前写过，根据课程ID查询章节和小节
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideoByCourseId(courseId);
        return UnifiedResult.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideo);
    }
}
