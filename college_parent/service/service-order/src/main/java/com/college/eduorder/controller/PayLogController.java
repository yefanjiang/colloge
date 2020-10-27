package com.college.eduorder.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.college.commonutils.UnifiedResult;
import com.college.eduorder.entity.PayLog;
import com.college.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @GetMapping("createNative/{orderNo}")
    public UnifiedResult createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return UnifiedResult.ok().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public UnifiedResult queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            //出错
            return UnifiedResult.error().message("支付出错");
        }
        if ("SUCCESS".equals(map.get("trade_state"))) {
            //如果成功
            //更改订单状态
            payLogService.updateOrderStatus(map);
            return UnifiedResult.ok().message("支付成功");
        }

        return UnifiedResult.ok().code(25000).message("支付中");
    }

}

