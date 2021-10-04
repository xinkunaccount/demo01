package com.atguigu.eduservice.controller.front;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;
    //分页查询讲师每页8条
    @PostMapping("getTeacherPageList/{page}/{limit}")
    @ApiOperation("分页查询讲师列表")
    public R getTeacher(@PathVariable long page,@PathVariable long limit){
        Page<EduTeacher> teacherPage=new Page<>(page,limit);

        Map<String,Object> map=this.teacherService.pageThecherList(teacherPage);


        return R.ok().data("map",map);

    }


    //根据讲师Id查询讲师的详细信息
    @GetMapping("getTeacherInfo/{id}")
    @ApiOperation("根据讲师Id查询讲师个课程的详细信息")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher teacher = this.teacherService.getById(id);
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = this.courseService.list(wrapper);
        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
