package com.ccit.assembler;

import com.ccit.bean.OrderCheckInternetCafeBean;
import com.ccit.entity.OrderInternetCafeEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.OrderInternetCafeVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class OrderInternetCafeAssembler implements Assembler<OrderCheckInternetCafeBean, OrderInternetCafeEntity, OrderInternetCafeVo> {

    public OrderInternetCafeEntity fromBean(OrderCheckInternetCafeBean bean) {
        OrderInternetCafeEntity entity = new OrderInternetCafeEntity();
        return entity;
    }


    public OrderInternetCafeVo toVo(OrderInternetCafeEntity entity) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        OrderInternetCafeVo vo = new OrderInternetCafeVo();
        vo.setId(entity.getId());
        vo.setOrderId(entity.getOrderId());
        vo.setProvince(entity.getProvince());
        vo.setCity(entity.getCity());
        vo.setInternetCafeName(entity.getInternetCafeName());
        vo.setContact(entity.getContact());
        vo.setContactPhone(entity.getContactPhone());
        vo.setPubIp(entity.getPubIp());
        vo.setPubGateway(entity.getPubGateway());
        vo.setBandwidth(entity.getBandwidthDown());
        vo.setOpenTime(date.format(entity.getOpenTime()));
        vo.setOrderStatus(entity.getOrderStatus());
        vo.setDisposeComment(entity.getWholesalerComment());
        vo.setWholesalerTime(entity.getWholesalerTime() != null ? time.format(entity.getWholesalerTime()) : "");
        vo.setManufacturerTime(entity.getManufacturerTime() != null ? time.format(entity.getManufacturerTime()) : "");

        if (entity.getBillingTime() != null) {
            vo.setBillingTime(date.format(entity.getBillingTime()));
        } else {
            vo.setBillingTime("");
        }

        return vo;
    }


    public List<OrderInternetCafeVo> toVos(List<OrderInternetCafeEntity> entities) {
        List<OrderInternetCafeVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}