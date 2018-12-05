package com.ccit.service;

import com.ccit.bean.OrderCheckInternetCafeBean;
import com.ccit.vo.OrderInternetCafeVo;

import java.util.List;

public interface OrderInternetCafeService {

    List<OrderInternetCafeVo> listOrder();

    OrderInternetCafeVo getOrder(long id);

    void checkOrder(OrderCheckInternetCafeBean orderCheckBean);
}