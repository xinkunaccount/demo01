package com.atguigu.eduservice.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoVo {

    @ApiModelProperty("小节Id")
    private String id;
    @ApiModelProperty("小节标题")
    private String title;
}
