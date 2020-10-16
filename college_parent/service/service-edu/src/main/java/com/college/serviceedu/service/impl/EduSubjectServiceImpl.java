package com.college.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.entity.EduSubject;
import com.college.serviceedu.entity.excel.SubjectData;
import com.college.serviceedu.entity.subject.FirstSubject;
import com.college.serviceedu.entity.subject.SecondSubject;
import com.college.serviceedu.listener.SubjectExcelListener;
import com.college.serviceedu.mapper.EduSubjectMapper;
import com.college.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-16
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20002, "添加课程失败");
        }
    }

    @Override
    public List<FirstSubject> getAllOneTwoSubject() {
        List<FirstSubject> list = new ArrayList<>();

        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);


        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubjectOne = oneSubjectList.get(i);

            FirstSubject firstSubject = new FirstSubject();
            BeanUtils.copyProperties(eduSubjectOne, firstSubject);

            List<SecondSubject> twoSubject = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject eduSubjectTwo = twoSubjectList.get(j);
                if (eduSubjectTwo.getParentId().equals(eduSubjectOne.getId())) {
                    SecondSubject secondSubject = new SecondSubject();
                    BeanUtils.copyProperties(eduSubjectTwo, secondSubject);
                    twoSubject.add(secondSubject);
                }
            }
            firstSubject.setChildren(twoSubject);

            list.add(firstSubject);
        }

        return list;
    }
}
