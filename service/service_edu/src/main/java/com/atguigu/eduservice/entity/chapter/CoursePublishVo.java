package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

/**
 * 课程发布确认信息类
 */
@Data
public class CoursePublishVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;

}
