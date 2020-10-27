package com.college.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.commonutils.JwtUtils;
import com.college.commonutils.MD5;
import com.college.educenter.entity.UcenterMember;
import com.college.educenter.entity.vo.RegisterVo;
import com.college.educenter.mapper.UcenterMemberMapper;
import com.college.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.college.servicebase.exceptionHandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-23
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Override
    public String login(UcenterMember ucenterMember) {
        String mobile = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "登陆失败！");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        if (mobileMember == null) {
            throw new MyException(20001, "手机号不存在！");
        }

        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new MyException(20001, "密码错误！");
        }

        if (mobileMember.getIsDisabled()) {
            throw new MyException(20001, "该账户被禁用！");
        }

        String token = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return token;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)
            || StringUtils.isEmpty(nickname)) {
            throw new MyException(20001, "注册失败！");
        }

        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!redisCode.equals(code)) {
            throw new MyException(20001, "注册失败！");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new MyException(20001, "手机号存在，注册失败！");
        }

        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickname);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setAvatar("https://yefan-college.oss-cn-shanghai.aliyuncs.com/singer/a.jpg");
        baseMapper.insert(ucenterMember);

    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegisterDay(day);
    }
}
