package com.ccit.dao;

import com.ccit.entity.PaymentEnterpriseEntity;

import java.util.List;

public interface PaymentEnterpriseDao extends BaseDao<PaymentEnterpriseEntity, Long> {

    List<PaymentEnterpriseEntity> findByOrderId(long orderId);

    PaymentEnterpriseEntity findLastOneByOrderId(long id);
}
