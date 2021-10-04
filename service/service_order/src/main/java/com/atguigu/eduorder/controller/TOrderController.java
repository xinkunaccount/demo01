package com.atguigu.eduorder.controller;


import com.atguigu.eduorder.entity.TOrder;
import com.atguigu.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-03
 */
@Controller
@RequestMapping("/eduorder/order")
@Api(description = "订单")
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    @GetMapping("getOrder/{courseId}/{orderId}")
    public boolean getByOrder(@PathVariable String courseId,@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper=new QueryWrapper<>();
        wrapper.eq("id",orderId);
        wrapper.eq("sourse_id",courseId);
        wrapper.eq("status",1);

        int count = this.orderService.count(wrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }


    }

}

