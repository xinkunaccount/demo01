package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程名称，模糊查询")
    private String title;

    @ApiModelProperty(value = "课程发布状态，Draft未发布 Normal已发布")
    private String status;

    @ApiModelProperty(value = "开始时间")
    private String begin;

    @ApiModelProperty(value = "结束时间")
    private String end;
}
