package com.college.educms.controller;

import com.college.commonutils.UnifiedResult;
import com.college.educms.entity.CrmBanner;
import com.college.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.List;

/**
 * <P>
 *     用户前台管理
 * </P>
 * @author winte
 */
@RestController
@RequestMapping("/educms/crm-banneruser")
//@CrossOrigin
public class BannerUserController {
    @Autowired
    private CrmBannerService crmBannerService;

    @GetMapping("getAllBanner")
    public UnifiedResult getAllBanner() {
        List<CrmBanner> crmBannerList = crmBannerService.getAllBanner();
        return UnifiedResult.ok().data("list", crmBannerList);
    }

    @GetMapping("getBannerList")
    public UnifiedResult getBannerList() {
        List<CrmBanner> crmBannerList = crmBannerService.getBannerList();
        return UnifiedResult.ok().data("bannerList", crmBannerList);
    }


}
