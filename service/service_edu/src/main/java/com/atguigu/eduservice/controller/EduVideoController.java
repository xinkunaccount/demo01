package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-26
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    @Autowired
    private VodClient vodClient;

    /**
     * 添加章节
     * @param eduVideo
     * @return
     */
    @PostMapping("addVideo")
    public R saveVideo(@RequestBody EduVideo eduVideo){
        this.videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节
     */

    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        EduVideo eduVideo = this.videoService.getById(id);

        if (eduVideo.getVideoSourceId()!=null){
            this.vodClient.deleteVideo(eduVideo.getVideoSourceId());
        }
        this.videoService.removeById(id);
        return R.ok();

    }


}

