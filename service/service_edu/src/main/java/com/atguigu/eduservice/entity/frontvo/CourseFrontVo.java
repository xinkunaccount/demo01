package com.atguigu.eduservice.entity.frontvo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
    @ApiModelProperty(value = "二级分类")
    private String subjectId;

    @ApiModelProperty(value = "一级分类")
    private String subjectParentId;

    @ApiModelProperty(value = "销售数量")
    private Long buyCountSort;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreateSort;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal priceSort;


}
