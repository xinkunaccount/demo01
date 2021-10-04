package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {


    @Override
    public String uploadAvatar(MultipartFile file) {
        //地域节点
        String endPoint = ConstantPropertiesUtils.END_POINT;
        //Id
        String keyId = ConstantPropertiesUtils.KEY_ID;
        //秘钥
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        //oss中创建的名称
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;


        try {
            //创建oss客户端实例
            OSS ossClient = new OSSClientBuilder().build(endPoint, keyId, keySecret);
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //生成唯一的UUID
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            //将生成的UUID和文件名拼接，保证文件名的唯一性
            fileName=uuid+fileName;

            //将文件按照日期分类
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName=datePath+"/"+fileName;

            //使用Oss对象中的putObject实现上传
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();

            //获取上传到阿里云上的文件路径
            String url = "https://" + bucketName + "." + endPoint + "/" + fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传头像时异常");
            return null;
        }

    }
}
