package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-26
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
@Api(description = "课程大纲")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @GetMapping("chapterList/{courseId}")
    public R getChapterAndVideoList(@PathVariable String courseId) {
        List<ChapterVo> chapterVoList = this.chapterService.getChapterList(courseId);

        return R.ok().data("chapterVoList", chapterVoList);
    }

    /**
     * 添加章节
     */
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {

        this.chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据Id查询章节
     */

    @GetMapping("getChapterInfo/{chapterId}")
    public R findById(@PathVariable String chapterId) {
        EduChapter eduChapter = this.chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    /**
     * 编辑章节
     */
    @PostMapping("updateChapter")
    public R update(@RequestBody EduChapter eduChapter) {
        this.chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节
     */
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = this.chapterService.deleteChapter(chapterId);

        if (flag){
            return R.ok();
        }else {
            return R.error();
        }


    }


}

