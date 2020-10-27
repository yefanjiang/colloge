package com.college.staservice.controller;


import com.college.commonutils.UnifiedResult;
import com.college.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("registerCount/{day}")
    public UnifiedResult registerCount(@PathVariable String day) {
        statisticsDailyService.registerCount(day);
        return UnifiedResult.ok();
    }

}

