package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApiClientImpl implements  VodClient {
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除小节视频失败");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个小节视频失败");
    }
}
