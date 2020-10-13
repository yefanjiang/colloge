package com.college.serviceedu.controller;

import com.college.commonutils.UnifiedResult;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-13
 */
@RestController
@RequestMapping("/serviceedu/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("findAll")
    public UnifiedResult findAllTeacher() {
        List<EduTeacher> teacherList = teacherService.list(null);
        return UnifiedResult.ok().data("items", teacherList);
    }

    /**
     * 逻辑删除的方法
     * @param id
     * @return UnifiedResult 一个标准化的输出
     */
    @DeleteMapping("{id}")
    public UnifiedResult removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);

        if (flag) {
            return UnifiedResult.ok();
        } else {
            return UnifiedResult.error();
        }
    }
}

