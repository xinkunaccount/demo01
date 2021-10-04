package com.atguigu.educms.controller;

import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @Cacheable(value = "banner",key = "'selectIndexList'")
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        QueryWrapper<CrmBanner> bannerWrapper=new QueryWrapper<>();
        bannerWrapper.orderByDesc("id");
        bannerWrapper.last("limit 2");
        List<CrmBanner> bannerList = this.bannerService.list(bannerWrapper);
        System.out.println(bannerList.get(1).getImageUrl()+"/***");
        return R.ok().data("list",bannerList);

    }


}
