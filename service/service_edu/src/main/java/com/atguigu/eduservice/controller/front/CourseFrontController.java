package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.OrderClient;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseAllInfoVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("conditionpage/{page}/{limit}")
    @ApiOperation("带条件分页查询")
    public R ConditionPageCourse(@PathVariable long page, long limit, @RequestBody(required = false) CourseFrontVo courseFrontVo) {

        Page<EduCourse> coursePage=new Page<>(page,limit);
       Map<String,Object> map= this.courseService.conditionPage(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    @PostMapping("courseInfo/{id}")
    @ApiOperation("查询课程详细信息")
    public R getCourseInfo(@PathVariable String id, HttpServletRequest request){
      CourseAllInfoVo courseAllInfoVo =this.courseService.getCourseInfo(id);
        List<ChapterVo> chapterList = this.chapterService.getChapterList(id);
        boolean byOrder = this.orderClient.getByOrder(id, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("course",courseAllInfoVo).data("chapterList",chapterList).data("isBuy",byOrder);
    }


}
