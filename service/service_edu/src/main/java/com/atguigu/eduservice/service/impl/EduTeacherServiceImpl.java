package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import feign.template.QueryTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-23
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> pageThecherList(Page<EduTeacher> teacherPage) {

        QueryWrapper<EduTeacher>wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id");
        this.baseMapper.selectPage(teacherPage,wrapper);
        List<EduTeacher> pageList = teacherPage.getRecords();
        Map<String,Object> map=new HashMap<>();
        long total = teacherPage.getTotal();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();

        boolean next = teacherPage.hasNext();
        boolean previous = teacherPage.hasPrevious();

        map.put("items",pageList);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("next",next);
        map.put("previous",previous);

        return map;
    }
}
