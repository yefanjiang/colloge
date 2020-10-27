package com.college.staservice.service;

import com.college.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
