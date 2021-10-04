package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-01
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String loginUser(UcenterMember ucenterMember);

    boolean register(RegisterVo registerVo);

    int getRegisterCount(String day);
}
