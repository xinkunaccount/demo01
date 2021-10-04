package com.atguigu.vod.controller;

import com.atguigu.commonutils.R;
import com.atguigu.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadvideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = this.vodService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("removeAlyVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        this.vodService.deleteVideo(id);
        return R.ok();
    }

    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    //根据视频Id播放视频
    @GetMapping("getPlayAuth/{id}")
    public R getPlatAuth(@PathVariable String id){
    String auth=this.vodService.getVideoPlayAuth(id);
    return R.ok().data("auth",auth);

    }


}
