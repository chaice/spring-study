package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.bean.OrderCheckInternetCafeBean;
import com.ccit.dao.AgencyInternetCafeDao;
import com.ccit.dao.CustomerInternetCafeDao;
import com.ccit.dao.OrderInternetCafeDao;
import com.ccit.entity.AgencyInternetCafeEntity;
import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.entity.OrderInternetCafeEntity;
import com.ccit.enums.OrderInternetCafeStatus;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.service.OrderInternetCafeService;
import com.ccit.vo.OrderInternetCafeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class OrderInternetCafeServiceImpl implements OrderInternetCafeService {

    @Autowired
    private OrderInternetCafeDao orderInternetCafeDao;

    @Autowired
    private CustomerInternetCafeDao customerInternetCafeDao;

    @Autowired
    private AgencyInternetCafeDao agencyInternetCafeDao;

    @Autowired
    private Assembler<OrderCheckInternetCafeBean, OrderInternetCafeEntity, OrderInternetCafeVo> orderInternetCafeAssembler;


    @Override
    public List<OrderInternetCafeVo> listOrder() {
        List<OrderInternetCafeEntity> orderEntityList = orderInternetCafeDao.findAll();

        List<OrderInternetCafeVo> orderVoList = new LinkedList<>();
        if (orderEntityList != null) {
            for (OrderInternetCafeEntity orderEntity : orderEntityList) {
                OrderInternetCafeVo orderVo = orderInternetCafeAssembler.toVo(orderEntity);

                CustomerInternetCafeEntity customerEntity = customerInternetCafeDao.findOne(orderEntity.getCustomerId());
                orderVo.setCustomerName(customerEntity != null ? customerEntity.getZionName() : "");

                AgencyInternetCafeEntity retailerEntity = agencyInternetCafeDao.findOne(orderEntity.getRetailerId());
                orderVo.setRetailerName(retailerEntity != null ? retailerEntity.getName() : "");

                AgencyInternetCafeEntity wholesalerEntity = agencyInternetCafeDao.findOne(orderEntity.getWholesalerId());
                orderVo.setWholesalerName(wholesalerEntity != null ? wholesalerEntity.getName() : "");

                orderVoList.add(orderVo);
            }
        }

        return orderVoList;
    }

    @Override
    public OrderInternetCafeVo getOrder(long id) {
        OrderInternetCafeEntity orderEntity = orderInternetCafeDao.findOne(id);

        OrderInternetCafeVo orderVo = null;
        if (orderEntity != null) {
            orderVo = orderInternetCafeAssembler.toVo(orderEntity);
            CustomerInternetCafeEntity customerEntity = customerInternetCafeDao.findOne(orderEntity.getCustomerId());
            orderVo.setCustomerName(customerEntity != null ? customerEntity.getZionName() : "");

            AgencyInternetCafeEntity retailerEntity = agencyInternetCafeDao.findOne(orderEntity.getRetailerId());
            orderVo.setRetailerName(retailerEntity != null ? retailerEntity.getName() : "");

            AgencyInternetCafeEntity wholesalerEntity = agencyInternetCafeDao.findOne(orderEntity.getWholesalerId());
            orderVo.setWholesalerName(wholesalerEntity != null ? wholesalerEntity.getName() : "");
        }

        return orderVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void checkOrder(OrderCheckInternetCafeBean orderCheckBean) {

        OrderInternetCafeEntity orderEntity = orderInternetCafeDao.findOne(orderCheckBean.getId());
        if (orderEntity == null) {
            throw new BusinessException(CustomerErrorConstants.ORDER_INTERNET_CAFE_NOT_EXIST);
        }

        if (!orderEntity.getOrderStatus().equals(OrderInternetCafeStatus.MANUFACTURER_CHECKING.getName())) {
            throw new BusinessException(CustomerErrorConstants.ORDER_INTERNET_CAFE_NOT_IN_CHECKING_STATUS);
        }

        orderEntity.setOrderStatus(orderCheckBean.getOrderStatus());
        orderEntity.setWholesalerComment(orderCheckBean.getDisposeComment());
        orderEntity.setManufacturerTime(new Timestamp(System.currentTimeMillis()));

        if(orderCheckBean.getOrderStatus().equals(OrderInternetCafeStatus.ACTIVATED.getName())){
            orderEntity.setOpenTime(orderCheckBean.getOpenTime());
            orderEntity.setBillingTime(orderCheckBean.getBillingTime());
            orderEntity.setCustomerId(orderCheckBean.getCustomerId());
        }

        orderInternetCafeDao.saveOrUpdate(orderEntity);
    }

}
