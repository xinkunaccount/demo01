package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师姓名，模糊查询")
    private String name;

    @ApiModelProperty(value = "讲师头衔，1高级讲师，2首席讲师 ")
    private Integer level;

    @ApiModelProperty(value = "开始时间")
    private String begin;

    @ApiModelProperty(value = "结束时间")
    private String end;



}
