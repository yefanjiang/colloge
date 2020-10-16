package com.college.serviceedu.service;

import com.college.serviceedu.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.serviceedu.entity.subject.FirstSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-16
 */
public interface EduSubjectService extends IService<EduSubject> {
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<FirstSubject> getAllOneTwoSubject();
}
