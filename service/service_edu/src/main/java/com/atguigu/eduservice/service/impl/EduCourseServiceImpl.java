package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.CoursePublishVo;
import com.atguigu.eduservice.entity.frontvo.CourseAllInfoVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.mapper.EduCourseDescriptionMapper;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-26
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;
    @Autowired
    private EduCourseMapper courseMapper;

    @Autowired
    private EduVideoMapper videoMapper;

    @Autowired
    private EduChapterMapper chapterMapper;

    @Autowired
    private EduCourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private VodClient vodClient;
    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //将courseInfoVo类转换成EduCourse对象，然后添加至数据库
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int count = baseMapper.insert(eduCourse);
        String courseId = null;
        if (count > 0) {
            courseId = eduCourse.getId();
            EduCourseDescription courseDescription = new EduCourseDescription();
            courseDescription.setId(courseId);
            courseDescription.setDescription(courseInfoVo.getDescription());
            this.courseDescriptionService.save(courseDescription);
        } else {
            throw new GuliException(20001, "添加课程信息时异常！！！");
        }

        return courseId;

    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {

        EduCourse eduCourse = this.baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        EduCourseDescription courseDescription = this.courseDescriptionService.getById(courseId);
        if (eduCourse.getId().equals(courseDescription.getId())) {
            courseInfoVo.setDescription(courseDescription.getDescription());
        }

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int count = baseMapper.updateById(eduCourse);
        if (count > 0) {
            EduCourseDescription description = new EduCourseDescription();
            //BeanUtils.copyProperties(courseInfoVo,description);'
            description.setId(courseInfoVo.getId());
            description.setDescription(courseInfoVo.getDescription());
            this.courseDescriptionService.updateById(description);
        } else {
            System.out.println("编辑课程信息时异常");
            throw new GuliException(20001, "编辑课程信息时异常！");
        }


    }

    /**
     * 确认发布消息
     *
     * @param courseId
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        CoursePublishVo coursePublishInfo = this.courseMapper.getCoursePublishInfo(courseId);
        return coursePublishInfo;
    }

    @Override
    public boolean removeCourse(String courseId) {
        //删除小节
        this.videoService.removeVideoByCourseId(courseId);
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        int chapterDel = this.chapterMapper.delete(chapterWrapper);

        int desc = 0;
        if (chapterDel > 0) {
            desc = this.courseDescriptionMapper.deleteById(courseId);
        }
        int courseDel = 0;
        if (desc > 0) {
            courseDel = this.courseMapper.deleteById(courseId);
        }

        return true;

    }

    //根据条件分页查询
    @Override
    public Map<String, Object> conditionPage(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper=new QueryWrapper<>();
        if (courseFrontVo.getSubjectParentId()!=null){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if (courseFrontVo.getSubjectId()!=null){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if (courseFrontVo.getBuyCountSort()!=null){
            wrapper.orderByDesc("buy_count");
        }

        if (courseFrontVo.getPriceSort()!=null){
            wrapper.orderByDesc("price");
        }
        if (courseFrontVo.getGmtCreateSort()!=null){
            wrapper.orderByDesc("gmt_create");
        }
        baseMapper.selectPage(coursePage,wrapper);

        List<EduCourse> courseList = coursePage.getRecords();

        Map<String,Object> map=new HashMap<>();
        long total = coursePage.getTotal();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();

        boolean next = coursePage.hasNext();
        boolean previous = coursePage.hasPrevious();

        map.put("items",courseList);
        map.put("total",total);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("next",next);
        map.put("previous",previous);

        return map;
    }

    @Override
    public CourseAllInfoVo getCourseInfo(String id) {



        return this.courseMapper.getCourseInfo(id);
    }
}
