package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-26
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {

        String courseId = this.courseService.saveCourseInfo(courseInfoVo);

        return R.ok().data("courseId", courseId);
    }

    @ApiOperation("根据课程Id查询课程信息")
    @GetMapping("findCourseById/{courseId}")
    public R findCourseById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = this.courseService.getCourseInfoById(courseId);

        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation("根据课程Id更新课程信息")
    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo) {
        this.courseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }

    /**
     * 根据课程Id 进行查询课程所有信息，进行确认
     */

    @ApiOperation("根据课程Id获取发布确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishInfo(@PathVariable String id) {
        CoursePublishVo coursePublishInfo = this.courseService.getCoursePublishInfo(id);

        return R.ok().data("publishCourse", coursePublishInfo);
    }


    //课程最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        boolean flag = this.courseService.updateById(course);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //查询课程列表

    @GetMapping("courseList")
    public R courseList() {
        List<EduCourse> list = this.courseService.list(null);
        return R.ok().data("list", list);
    }

    //根据条件分页查询
    @PostMapping("courseConditionPage/{current}/{limit}")
    public R conditionPage(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) CourseQuery courseQuery) {
        //分页查询
        Page<EduCourse> pageCourse = new Page<>(current, limit);
        String title = courseQuery.getTitle();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        String status = courseQuery.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);

        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);

        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        wrapper.orderByDesc("gmt_create");
        this.courseService.pageMaps(pageCourse, wrapper);
        long total = pageCourse.getTotal();
        List<EduCourse> pageCourseList = pageCourse.getRecords();
        return R.ok().data("rows", pageCourseList).data("total", total);
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R removeCourse(@PathVariable String courseId){
      boolean flag=  this.courseService.removeCourse(courseId);
      if (flag) {
          return R.ok();
      }else {
          return  R.error();
      }
    }


}

