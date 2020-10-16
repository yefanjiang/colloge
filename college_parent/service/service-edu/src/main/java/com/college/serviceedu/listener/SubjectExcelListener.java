package com.college.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.serviceedu.entity.EduSubject;
import com.college.serviceedu.entity.excel.SubjectData;
import com.college.serviceedu.service.EduSubjectService;

import java.util.Map;

/**
 * @author winte
 * 因为SubjectExcelListener不能交给spring管理，需要自己new，不能注入其他对象
 * 不能实现数据库操作
 * 因此要改造函数，写一个构造器，手动传到实现类里
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new MyException(20001, "文件为空");
        }

        // 按照行读取，每次有两个值，分别为一级分类和二级分类
        // 判断一级分类是否重复
        EduSubject existFirstSubject = this.existFirstSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (existFirstSubject == null) {
            existFirstSubject = new EduSubject();
            existFirstSubject.setParentId("0");
            existFirstSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existFirstSubject);
        }

        String pid = existFirstSubject.getId();
        // 判断二级分类是否重复
        EduSubject existSecondSubject = this.existSecondSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (existSecondSubject == null) {
            existSecondSubject = new EduSubject();
            existSecondSubject.setParentId(pid);
            existSecondSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existSecondSubject);
        }
    }

    private EduSubject existFirstSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject firstSubject = eduSubjectService.getOne(wrapper);
        return firstSubject;
    }

    private EduSubject existSecondSubject(EduSubjectService eduSubjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject secondSubject = eduSubjectService.getOne(wrapper);
        return secondSubject;
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
