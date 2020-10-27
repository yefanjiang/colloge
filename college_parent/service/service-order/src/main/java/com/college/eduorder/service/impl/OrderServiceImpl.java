package com.college.eduorder.service.impl;

import com.college.commonutils.ordervo.CourseWebVoOrder;
import com.college.commonutils.ordervo.UcenterMemberOrder;
import com.college.eduorder.client.EduClient;
import com.college.eduorder.client.UcenterClient;
import com.college.eduorder.entity.Order;
import com.college.eduorder.mapper.OrderMapper;
import com.college.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.college.eduorder.utils.OrderNoUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yefanjiang
 * @since 2020-10-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient educlient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 通过远程调用的方式获取用户信息以及课程的信息
     * @param courseId 课程ID
     * @param memberIdByJwtToken 用户ID
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberIdByJwtToken) {
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberIdByJwtToken);

        CourseWebVoOrder courseInfoOrder = educlient.getCourseInfoOrder(courseId);

        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);
        // 支付类型 1是微信
        order.setPayType(1);
        baseMapper.insert(order);

        return order.getOrderNo();


    }
}
