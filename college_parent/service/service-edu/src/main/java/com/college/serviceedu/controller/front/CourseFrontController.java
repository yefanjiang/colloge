package com.college.serviceedu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.JwtUtils;
import com.college.commonutils.UnifiedResult;
import com.college.commonutils.ordervo.CourseWebVoOrder;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.client.OrderClient;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.entity.frontvo.CourseFrontVo;
import com.college.serviceedu.entity.frontvo.CourseWebVo;
import com.college.serviceedu.entity.vo.ChapterVo;
import com.college.serviceedu.service.EduChapterService;
import com.college.serviceedu.service.EduCourseService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author winte
 */
@RestController
//@CrossOrigin
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

    @Autowired
    private OrderClient orderClient;

    @GetMapping("getFrontCourseInfo/{courseId}")
    public UnifiedResult getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);

        // 之前写过，根据课程ID查询章节和小节
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideoByCourseId(courseId);

        // 在order里新加，根据课程ID和用户ID查询当前课程是否已经支付过了
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (memberId == null) {
            throw new MyException(20001, "请先登陆！");
        }
        boolean buyCourse = orderClient.isBuyCourse(courseId, memberId);

        return UnifiedResult.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideo)
                .data("isBuy", buyCourse);
    }

    /**
     * 根据课程ID查询课程信息
     */
    @PostMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId) {
        CourseWebVo baseCourseInfo = eduCourseService.getBaseCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(baseCourseInfo, courseWebVoOrder);
        return courseWebVoOrder;
    }
}
