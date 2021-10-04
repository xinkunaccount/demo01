package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import sun.security.x509.EDIPartyName;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public EduSubjectService eduSubjectService;

    public SubjectExcelListener() {

    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData data, AnalysisContext context) {
            if (data==null){
                throw new GuliException(20001,"读取文件为空");
            }
        EduSubject exitsOneSubject = this.existOneSubject(eduSubjectService, data.getOneSubjectName());
        if (exitsOneSubject==null){
            exitsOneSubject=new EduSubject();
            exitsOneSubject.setParentId("0");
            exitsOneSubject.setTitle(data.getOneSubjectName());
            eduSubjectService.save(exitsOneSubject);
        }
        //获取一级的Id值
        String pid=exitsOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, data.getTwoSubjectName(), pid);
        if (existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(data.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }


    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = eduSubjectService.getOne(wrapper);
        return oneSubject;

    }
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){

        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", pid);
        EduSubject twoSubject = eduSubjectService.getOne(wrapper);
        return twoSubject;

    }
}
