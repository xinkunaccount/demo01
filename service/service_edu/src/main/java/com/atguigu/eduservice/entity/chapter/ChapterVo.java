package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    @ApiModelProperty("章节Id")
    private String id;
    @ApiModelProperty("章节标题")
    private String title;

    private List<VideoVo> children = new ArrayList<>();


}
