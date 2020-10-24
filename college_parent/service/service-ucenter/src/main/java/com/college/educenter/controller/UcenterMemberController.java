package com.college.educenter.controller;


import com.college.commonutils.JwtUtils;
import com.college.commonutils.UnifiedResult;
import com.college.educenter.entity.UcenterMember;
import com.college.educenter.entity.vo.RegisterVo;
import com.college.educenter.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/educenter/ucenter-member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 登陆
     */
    @PostMapping("login")
    public UnifiedResult loginUser(@RequestBody UcenterMember ucenterMember) {
        // 返回token，使用JWT生成
        String token = ucenterMemberService.login(ucenterMember);
        return UnifiedResult.ok().data("token", token);
    }

    /**
     * 注册
     */
    @PostMapping("register")
    public UnifiedResult registerUser(@RequestBody RegisterVo registerVo) {
        // 返回token，使用JWT生成
        ucenterMemberService.register(registerVo);
        return UnifiedResult.ok();
    }

    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public UnifiedResult getMemberInfo(HttpServletRequest request) {
        // 调用jwt工具类的方法，根据request对象获取头信息，返回用户id
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库，根据用户id获取用户信息
        UcenterMember ucenterMember = ucenterMemberService.getById(memberIdByJwtToken);
        return UnifiedResult.ok().data("userInfo", ucenterMember);
    }
}

