package com.atguigu.educenter.service.impl;

import com.alibaba.nacos.common.util.Md5Utils;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-01
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

@Autowired
private UcenterMemberMapper ucenterMemberMapper;


    @Override
    public String loginUser(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        System.out.println(MD5.encrypt(password));
        if (mobile == null || password == null) {
            throw new GuliException(20001, "登录参数为空");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        String token = null;
        if (mobileMember != null) {
            //获取密码进行加密
            String pwd =mobileMember.getPassword();
            if (MD5.encrypt(password).equals(pwd)) {
                if (!mobileMember.getIsDisabled()) {
                    token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
                } else {
                    throw new GuliException(20001, "该用户已被禁用");
                }

            } else {
                throw new GuliException(20001, "密码错误");
            }
        } else {
            throw new GuliException(20001, "该手机号不存在");
        }


        return token;
    }

    /**
     * 注册
     * @param registerVo
     * @return
     */
    @Override
    public boolean register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if (code==null || mobile==null ||nickname==null ||password==null ){
            throw new GuliException(20001,"注册信息不全");
        }
        //先从redis中获取验证码，然后和传入的验证码比较
        String registerCode=redisTemplate.opsForValue().get(mobile);
        if (!code.equals(registerCode)){
            throw new GuliException(20001,"验证码错误");
        }
        //判断手机号码是否重复
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count!=0){
            throw new GuliException(20001,"手机号码错误");
        }
        
        UcenterMember ucenterMember=new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/jJHyeM0EN2jhB70LntI3k8fEKe7W6CwykrKMgDJM4VZqCpcxibVibX397p0vmbKURGkLS4jxjGB0GpZfxCicgt07w/132");


        int insert = baseMapper.insert(ucenterMember);

        return insert>0;
    }

    //根据时间查询注册人数
    @Override
    public int getRegisterCount(String day) {



        return   this.ucenterMemberMapper.getRegisterCount(day);
    }
}
