package com.ccit.assembler;

import com.ccit.bean.OrderCheckEnterpriseBean;
import com.ccit.entity.OrderEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.OrderEnterpriseVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class OrderEnterpriseAssembler implements Assembler<OrderCheckEnterpriseBean, OrderEnterpriseEntity, OrderEnterpriseVo> {

    public OrderEnterpriseEntity fromBean(OrderCheckEnterpriseBean bean) {
        OrderEnterpriseEntity entity = new OrderEnterpriseEntity();
        return entity;
    }


    public OrderEnterpriseVo toVo(OrderEnterpriseEntity entity) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        OrderEnterpriseVo vo = new OrderEnterpriseVo();
        vo.setId(entity.getId());
        vo.setOrderId(entity.getOrderId());
        vo.setProvince(entity.getProvince());
        vo.setCity(entity.getCity());
        vo.setAddress(entity.getAddress());
        vo.setEnterpriseName(entity.getEnterpriseName());
        vo.setContact(entity.getContact());
        vo.setContactPhone(entity.getContactPhone());
        vo.setBandwidth(entity.getBandwidthDown());
        vo.setOpenTime(date.format(entity.getOpenTime()));
        vo.setOrderStatus(entity.getOrderStatus());
        vo.setDisposeComment(entity.getWholesalerComment());
        vo.setWholesalerTime(entity.getWholesalerTime() != null ? time.format(entity.getWholesalerTime()) : "");
        vo.setManufacturerTime(entity.getManufacturerTime() != null ? time.format(entity.getManufacturerTime()) : "");
        vo.setPaymentPeriod(entity.getPaymentPeriod() !=null ? entity.getPaymentPeriod() : 0);
        vo.setPaymentType(entity.getPaymentType());
        vo.setPubIp(entity.getPubIp());
        vo.setPubGateway(entity.getPubGateway());
        vo.setNetmask(entity.getNetmask());
        vo.setMachineNumber(entity.getMachineNumber());

        if (entity.getBillingTime() != null) {
            vo.setBillingTime(date.format(entity.getBillingTime()));
        } else {
            vo.setBillingTime("");
        }

        return vo;
    }


    public List<OrderEnterpriseVo> toVos(List<OrderEnterpriseEntity> entities) {
        List<OrderEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}