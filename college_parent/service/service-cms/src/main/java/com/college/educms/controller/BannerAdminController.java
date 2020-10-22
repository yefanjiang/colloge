package com.college.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.college.commonutils.UnifiedResult;
import com.college.educms.entity.CrmBanner;
import com.college.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台banner管理，主要是管理员权限使用
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-22
 */
@RestController
@RequestMapping("/educms/crm-banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    CrmBannerService crmBannerService;

    @GetMapping("getBannerAdminList/{page}/{limit}")
    public UnifiedResult getBannerAdminList(@PathVariable long page, @PathVariable long limit) {
        Page<CrmBanner> pageList = new Page<>(page, limit);
        crmBannerService.page(pageList, null);
        return UnifiedResult.ok().data("items", pageList.getRecords()).data("total", pageList.getTotal());
    }

    @GetMapping("getBanner/{id}")
    public UnifiedResult getBanner(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return UnifiedResult.ok().data("item", banner);
    }

    @PostMapping("addBanner")
    public UnifiedResult addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return UnifiedResult.ok();
    }

    @PostMapping("updateBanner")
    public UnifiedResult updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return UnifiedResult.ok();
    }

    @DeleteMapping("deleteBanner/{id}")
    public UnifiedResult deleteBanner(@PathVariable String id) {
        crmBannerService.removeById(id);
        return UnifiedResult.ok();
    }

}

