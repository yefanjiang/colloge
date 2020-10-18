package com.college.serviceedu.controller;


import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.vo.ChapterVo;
import com.college.serviceedu.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
@RestController
@RequestMapping("/serviceedu/edu-chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     * 根据课程ID查询课程大纲
     * @return
     */
    @GetMapping("getChapterVideo/{courseId}")
    public UnifiedResult getChapterVideo(@PathVariable String courseId) {

        List<ChapterVo> chapterVoList = eduChapterService.getChapterVideoByCourseId(courseId);
        return UnifiedResult.ok().data("allChapterVideo", chapterVoList);
    }



}

