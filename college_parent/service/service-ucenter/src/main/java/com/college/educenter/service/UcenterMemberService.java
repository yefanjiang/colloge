package com.college.educenter.service;

import com.college.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.college.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-23
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember ucenterMember);

    void register(RegisterVo registerVo);

    Integer countRegister(String day);
}
