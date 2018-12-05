package com.ccit.assembler;

import com.ccit.bean.PaymentEnterpriseBean;
import com.ccit.entity.PaymentEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.PaymentEnterpriseVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class PaymentEnterpriseAssembler implements Assembler<PaymentEnterpriseBean, PaymentEnterpriseEntity, PaymentEnterpriseVo> {

    public PaymentEnterpriseEntity fromBean(PaymentEnterpriseBean bean) {
        PaymentEnterpriseEntity entity = new PaymentEnterpriseEntity();
        entity.setAmount(new BigDecimal(bean.getAmount()));
        entity.setOperator(bean.getOperator());
        entity.setRecordTime(new Timestamp(System.currentTimeMillis()));
        entity.setPaymentDate(bean.getPaymentDate());
        return entity;
    }


    public PaymentEnterpriseVo toVo(PaymentEnterpriseEntity entity) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        PaymentEnterpriseVo vo = new PaymentEnterpriseVo();
        vo.setId(entity.getId());
        vo.setOrderId(entity.getOrderId());
        vo.setAmount(entity.getAmount().toString());
        vo.setBillingDate(date.format(entity.getBillingDate()));
        vo.setPaymentDate(date.format(entity.getPaymentDate()));
        vo.setOperator(entity.getOperator());

        return vo;
    }


    public List<PaymentEnterpriseVo> toVos(List<PaymentEnterpriseEntity> entities) {
        List<PaymentEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}