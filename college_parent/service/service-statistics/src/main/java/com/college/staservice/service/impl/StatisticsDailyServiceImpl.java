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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, Object> getShowDate(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated", begin, end);
        queryWrapper.select("date_calculated", type);
        List<StatisticsDaily> list = baseMapper.selectList(queryWrapper);

        // 根据数据进行封装, 前端要求返回json，对应后端的list集合
        List<String> dateList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            StatisticsDaily daily = list.get(i);
            dateList.add(daily.getDateCalculated());

            switch (type) {
                case "login_num" :
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("numDataList", numDataList);

        return map;
    }
}
