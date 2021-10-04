package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl  implements MsmService {
    @Override
    public boolean send(Map<String, Object> map, String phone) {
        if (phone!=null&&phone.length()!=11){
            return false;
        }
        DefaultProfile profile=DefaultProfile.getProfile("default","LTAI5tLcby5Lus7vEfceqsGL","zNU3cIL0ATHt255blcSieQWWDdsCLI");
        IAcsClient client=new DefaultAcsClient(profile);

        //设置相关参数
        CommonRequest request=new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setSysAction("SendSms");
        //设置发送参数
        request.putQueryParameter("PhoneNumbers",phone);//设置发送的手机号
        request.putQueryParameter("SignName","签名名称");//设置发送的手机号
        request.putQueryParameter("TemplateCode","模板code");//设置发送的手机号
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));//设置发送的手机号，将map集合转换成json形式你
        try {
            CommonResponse response=client.getCommonResponse(request);
            boolean result = response.getHttpResponse().isSuccess();
            return result;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
