package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page,@PathVariable long limit){
   Page<CrmBanner> bannerPage=new Page<>(page,limit);

        this.bannerService.page(bannerPage,null);

        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //添加Banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner banner){
        this.bannerService.save(banner);
        return R.ok();
    }

    @GetMapping("getById/{id}")
    public R getBannerById(@PathVariable String id){
        this.bannerService.getById(id);
        return R.ok();

    }



    //编辑Banner
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner banner){
        this.bannerService.updateById(banner);
        return R.ok();
    }

    //删除Banner
    @DeleteMapping("remove/{id}")
    public R removeBanner(@PathVariable String id ){

        boolean flag=this.bannerService.removeById(id);
        if (flag) {
            return R.ok();
        }else {
            return R.error().message("删除banner异常！");
        }
    }


}

