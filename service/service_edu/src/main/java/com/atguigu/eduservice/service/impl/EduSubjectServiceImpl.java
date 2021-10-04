package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-25
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream=file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getSubjectList() {
        //查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper=new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneWrapper);
        //查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper=new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoWrapper);
        List<OneSubject> finalSubjectList=new ArrayList<>();

        for (int i = 0; i <oneSubjectList.size(); i++) {
            //获取集合中一级分类的单个对象
            EduSubject eduSubject = oneSubjectList.get(i);
            //创建一级分类的对象
            OneSubject oneSubject=new OneSubject();
            //将课程分类对象的Id设置在一级分类对象中
            //oneSubject.setId(eduSubject.getId());
            //设置title
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //将一级分类添加至最终的集合中
            finalSubjectList.add(oneSubject);
            List<TwoSubject> finalTwoSubject=new ArrayList<>();
            for (int j=0;j<twoSubjectList.size();j++){
                    EduSubject twoSub=twoSubjectList.get(j);
                    if (eduSubject.getId().equals(twoSub.getParentId())){
                        TwoSubject twoSubject=new TwoSubject();
                        //将
                        BeanUtils.copyProperties(twoSub,twoSubject);
                        finalTwoSubject.add(twoSubject);
                    }
            }
            oneSubject.setChildren(finalTwoSubject);

        }

        return finalSubjectList;
    }
}
