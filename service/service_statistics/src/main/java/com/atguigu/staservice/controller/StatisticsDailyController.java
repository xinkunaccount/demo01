package com.atguigu.staservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-03
 */
@RestController
@RequestMapping("/staservice/statistics")
@ComponentScan("com.atguigu")
@CrossOrigin
@EnableDiscoveryClient
@EnableFeignClients

public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    //统计当天注册人数
    @PostMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
         this.staService.countRegister(day);
         return R.ok();
    }


    //根据查询条件查询返回不同的数据
    @GetMapping("getData/{type}/{begin}/{end}")
    public R getData(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String,Object> map =this.staService.getData(type,begin,end);
        return R.ok().data(map);
    }





}

