package com.college.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.college.commonutils.JwtUtils;
import com.college.commonutils.UnifiedResult;
import com.college.eduorder.entity.Order;
import com.college.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder/{courseId}")
    public UnifiedResult createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        String orderNumber = orderService.createOrders(courseId, memberIdByJwtToken);
        return UnifiedResult.ok().data("orderId", orderNumber);
    }

    @GetMapping("getOrderInfo/{id}")
    public UnifiedResult getOrderInfo(@PathVariable String id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
        wrapper.eq("order_no", id);
        Order order = orderService.getOne(wrapper);
        return UnifiedResult.ok().data("item", order);
    }

    /**
     * 根据课程ID和用户ID查询订单表中订单状态
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        int count = orderService.count(wrapper);
        if (count > 0) {
            return true;
        } else {
            return false;
        }

    }
}

