package com.college.staservice.controller;


import com.college.commonutils.UnifiedResult;
import com.college.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    /**
     * 图表显示，返回两部分数据
     * @param type
     * @param begin
     * @param end
     * @return 日期的json数组，数量的json数组
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public UnifiedResult showData(@PathVariable String type, @PathVariable String begin,
                                  @PathVariable String end) {
        Map<String, Object> map = statisticsDailyService.getShowDate(type, begin, end);
        return UnifiedResult.ok().data(map);
    }
}

