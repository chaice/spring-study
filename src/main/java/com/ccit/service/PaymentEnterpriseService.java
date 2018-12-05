package com.ccit.service;

import com.ccit.bean.PaymentEnterpriseBean;
import com.ccit.vo.PaymentEnterpriseVo;

import java.util.List;

public interface PaymentEnterpriseService {

    List<PaymentEnterpriseVo> listPaymentByOrderId(long orderId);

    PaymentEnterpriseVo getPayment(long paymentId);

    void removePayment(long paymentId);

    void createPayment(long orderId, PaymentEnterpriseBean paymentEnterpriseBean);

    void modifyPayment(long paymentId, PaymentEnterpriseBean paymentEnterpriseBean);
}