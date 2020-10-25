package com.college.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.serviceedu.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-13
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> eduTeacherPage);
}
