package com.college.staservice.service;

import com.college.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);

    /**
     * 图表显示，返回两部分数据
     * @param type
     * @param begin
     * @param end
     * @return
     */
    Map<String,Object> getShowDate(String type, String begin, String end);
}
