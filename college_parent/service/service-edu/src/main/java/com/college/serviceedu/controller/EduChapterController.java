package com.college.serviceedu.controller;


import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.EduChapter;
import com.college.serviceedu.entity.EduCourse;
import com.college.serviceedu.entity.vo.ChapterVo;
import com.college.serviceedu.service.EduChapterService;
import org.omg.CORBA.PUBLIC_MEMBER;
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
//@CrossOrigin
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

    @PostMapping("addChapter")
    public UnifiedResult addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return UnifiedResult.ok();
    }

    @GetMapping("getChapter/{chapterId}")
    public UnifiedResult getChapter(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return UnifiedResult.ok().data("chapter", eduChapter);
    }

    @PostMapping("updateChapter")
    public UnifiedResult updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return UnifiedResult.ok();
    }

    /**
     * 要考虑章节中有没有小节的问题
     * @param chapterId
     * @return
     */
    @DeleteMapping("deleteChapter/{chapterId}")
    public UnifiedResult deleteChapter(@PathVariable String chapterId) {
        boolean canDelete = eduChapterService.deleteChapter(chapterId);
        if (canDelete) {
            return UnifiedResult.ok();
        } else {
            return UnifiedResult.error();
        }
    }
}

