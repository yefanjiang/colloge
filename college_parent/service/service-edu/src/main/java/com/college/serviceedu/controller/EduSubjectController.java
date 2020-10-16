package com.college.serviceedu.controller;


import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.subject.FirstSubject;
import com.college.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/serviceedu/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("addSubject")
    public UnifiedResult addSubject(MultipartFile file) {
        eduSubjectService.saveSubject(file, eduSubjectService);
        return UnifiedResult.ok();
    }

    @GetMapping("getAllSubject")
    public UnifiedResult getAllSubject() {
        List<FirstSubject> list = eduSubjectService.getAllOneTwoSubject();
        return UnifiedResult.ok().data("list", list);
    }
}

