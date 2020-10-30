package com.college.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.UnifiedResult;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.entity.EduTeacher;
import com.college.serviceedu.entity.vo.TeacherQuery;
import com.college.serviceedu.service.EduTeacherService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//@CrossOrigin
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

    /**
     *
     * @param current 当前页
     * @param limit 一共显示的页数
     * @return
     */
    @GetMapping("{current}/{limit}")
    public UnifiedResult pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        teacherService.page(eduTeacherPage, null);

        long total = eduTeacherPage.getTotal();
        List<EduTeacher> teacherList = eduTeacherPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", teacherList);
        return UnifiedResult.ok().data(map);

    }


    /**
     *
     * @param current 分页
     * @param limit 分页
     * @param teacherQuery 传入一个对象，利用这个对象进行条件查询
     * @return
     * @RequestBody(required = false) TeacherQuery teacherQuery
     * 使用json进行传递 注意 需要使用post传递 @PostMapping("pageTeacherCondition/{current}/{limit}")
     */
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public UnifiedResult pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                              @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        // 根据时间排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(eduTeacherPage, wrapper);
        long total = eduTeacherPage.getTotal();
        List<EduTeacher> teacherList = eduTeacherPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", teacherList);
        return UnifiedResult.ok().data(map);
    }

    /**
     *
     * @param eduTeacher
     * @return
     */
    @PostMapping("addTeacher")
    public UnifiedResult addTeacher(@RequestBody (required = true) EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);

        if (save) {
            return UnifiedResult.ok();
        } else {
            return UnifiedResult.error();
        }
    }

    @GetMapping("getTeacherById/{id}")
    public UnifiedResult getTeacherById(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return UnifiedResult.ok().data("teacher", eduTeacher);
    }

    @PostMapping("updateTeacher")
    public UnifiedResult updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return UnifiedResult.ok();
        } else {
            return UnifiedResult.error();
        }
    }


}

