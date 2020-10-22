package com.college.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.educms.entity.CrmBanner;
import com.college.educms.mapper.CrmBannerMapper;
import com.college.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-22
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    @Override
    public List<CrmBanner> getAllBanner() {
        List<CrmBanner> crmBannerList = baseMapper.selectList(null);
        return crmBannerList;
    }

    @Cacheable(value = "banner", key = "'getBannerList'")
    @Override
    public List<CrmBanner> getBannerList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        // last方法 拼接sql 只取两条数据
        wrapper.last("limit 2");

        List<CrmBanner> crmBannerList = baseMapper.selectList(wrapper);
        return crmBannerList;
    }
}
