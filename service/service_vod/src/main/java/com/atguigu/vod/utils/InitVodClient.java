package com.atguigu.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitVodClient {

    public static DefaultAcsClient initClient(String accessKeyId,String accessKeySecret){

        String regionId="cn-shanghai";
        //获取默认配置
        DefaultProfile profile=DefaultProfile.getProfile(regionId,accessKeyId,accessKeySecret);

        DefaultAcsClient client=new DefaultAcsClient(profile);
        return client;

    }

}
