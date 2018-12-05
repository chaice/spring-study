package com.ccit.service;

import com.ccit.bean.OrderCheckEnterpriseBean;
import com.ccit.vo.OrderEnterpriseVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface OrderEnterpriseService {

    List<OrderEnterpriseVo> listOrder(String province,String collection,String enterpriseName);

    OrderEnterpriseVo getOrder(long id);

    void checkOrder(OrderCheckEnterpriseBean orderCheckBean);

    void updatePartOrder(OrderCheckEnterpriseBean orderCheckBean);

    void exportOrder(HttpServletResponse response, String province, String collection,String enterpriseName);

    void switchOrder(OrderCheckEnterpriseBean orderCheckBean);
}