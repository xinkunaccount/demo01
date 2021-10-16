package com.atguigu.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.InitVodClient;
import com.atguigu.vod.utils.UploadVideoUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {


    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            //获取文件名字
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(UploadVideoUtils.ACCESS_KEY_ID, UploadVideoUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }
            return videoId;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    //删除阿里云视频
    @Override
    public void deleteVideo(String id) {
        //初始化
        DefaultAcsClient client = InitVodClient.initClient(UploadVideoUtils.ACCESS_KEY_ID,UploadVideoUtils.ACCESS_KEY_SECRET);
        DeleteVideoRequest request=new DeleteVideoRequest();
        // 向删除请求中设置视频Id
        request.setVideoIds(id);
        try {
            DeleteVideoResponse response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initClient(UploadVideoUtils.ACCESS_KEY_ID, UploadVideoUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            String videoIds = StringUtils.join(videoIdList.toArray(), ",");

            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    @Override
    public String getVideoPlayAuth(String id) {


        try {
            DefaultAcsClient client = InitVodClient.initClient(UploadVideoUtils.ACCESS_KEY_ID, UploadVideoUtils.ACCESS_KEY_SECRET);
            GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
            request.setVideoId(id);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String auth = response.getPlayAuth();
            return auth;
        } catch (ClientException e) {
        throw  new GuliException(20001,"获取凭证失败");
        }

    }
}
