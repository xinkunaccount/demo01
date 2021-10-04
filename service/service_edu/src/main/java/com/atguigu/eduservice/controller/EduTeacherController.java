package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation("查找所有讲师列表")
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> list = this.eduTeacherService.list(null);
        return R.ok().data("list", list);
    }

    @ApiOperation(value = "根据Id逻辑删除讲师信息")
    @DeleteMapping("{id}")
    public R remove(@ApiParam(name = "id", value = "讲师Id", required = true) @PathVariable String id) {

        if (this.eduTeacherService.removeById(id)) {
            return R.ok();
        }
        return R.error();
    }


    @ApiOperation(value = "分页查询")
    @GetMapping("/byPage/{current}/{limit}")
    public R byPage(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        this.eduTeacherService.page(pageTeacher, null);
        Long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        if (records != null && records.size() > 0) {
            return R.ok().data("records", records).data("total", total);
        }

        return R.error();
    }

    @ApiOperation(value = "根据条件分页查询讲师")
    @PostMapping("/teacherCondition/{current}/{limit}")
    public R pageByCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)) {
            //构建条件
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");


        this.eduTeacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {

        boolean result = this.eduTeacherService.save(eduTeacher);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据Id查找")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){

        EduTeacher eduTeacher = this.eduTeacherService.getById(id);
            return R.ok().data("teacher",eduTeacher);

    }

    @ApiOperation(value = "更新讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
         boolean flag=this.eduTeacherService.updateById(eduTeacher);

         if (flag){
             return R.ok();
         }else {
             return R.error();
         }
    }


}

