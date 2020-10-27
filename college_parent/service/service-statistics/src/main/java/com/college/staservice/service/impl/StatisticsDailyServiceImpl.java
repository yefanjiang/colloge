package com.college.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.commonutils.UnifiedResult;
import com.college.servicebase.exceptionHandler.MyException;
import com.college.staservice.client.UcenterClient;
import com.college.staservice.entity.StatisticsDaily;
import com.college.staservice.mapper.StatisticsDailyMapper;
import com.college.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        if (day == null) {
            throw new MyException(20001, "请选择日期进行查询");
        }

        //删除已存在的统计对象 以便更新
        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.eq("date_calculated", day);
        baseMapper.delete(dayQueryWrapper);

        UnifiedResult unifiedResult = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) unifiedResult.getData().get("countRegister");

        Integer loginNum = RandomUtils.nextInt(100, 200);
        //TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);
        //TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);
        //TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }
}
