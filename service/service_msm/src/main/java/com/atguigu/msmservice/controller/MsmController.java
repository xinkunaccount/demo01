package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm/")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("send/{phone}")
    public R senMessage(@PathVariable String phone){
        //先从redis中获取验证码，获取不到再生产
        String code= redisTemplate.opsForValue().get(phone);
        System.out.println("redis中获取短信验证码为"+code);
        if (code!=null){
            R.ok().data("code",code);
        }

         code= RandomUtils.getFourBitRandom();
        System.out.println("生成短信验证码为"+code);
        //将验证码以map形式转发值阿里云的短信发送服务
        Map<String,Object> map=new HashMap<>();
        map.put("code",code);
        //调用发送方法
      boolean isSend=  this.msmService.send(map,phone);
        if (isSend) {
            //如果发送成功将验证码设置到redis中
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);//将验证在redis中的有效值设置为5分钟
            return R.ok();
        }else {
            return R.error().message("发送短信失败");
        }
    }


}
