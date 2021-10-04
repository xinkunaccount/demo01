package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-01
 */
@RestController
@RequestMapping("/educenter/member/")
@CrossOrigin
@Api(description = "用户登录注册")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    //登录
    @PostMapping("login")
    @ApiOperation("登录")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = this.ucenterMemberService.loginUser(ucenterMember);

        return R.ok().data("token", token);
    }


    //注册

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterVo registerVo){

       boolean flag= this.ucenterMemberService.register(registerVo);
        if (flag) {
            return R.ok();
        }else {
            return R.error().message("注册失败");
        }
    }


    //根据token获取用户信息
    @GetMapping("getInfoByToken")
    public R getInfoBtToken(HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = this.ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo",ucenterMember);
    }

    //查询每天的注册人数
    @GetMapping("getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String  day){

    int count= this.ucenterMemberService.getRegisterCount(day);

    return R.ok().data("count",count);
    }


}

