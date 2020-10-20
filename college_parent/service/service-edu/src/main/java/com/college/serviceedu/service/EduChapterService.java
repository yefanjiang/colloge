package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-18
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String id);
}
