package com.ccit.service.impl;

import com.ccit.exception.ResourceException;
import com.ccit.bean.PaymentEnterpriseBean;
import com.ccit.dao.OrderEnterpriseDao;
import com.ccit.dao.PaymentEnterpriseDao;
import com.ccit.entity.OrderEnterpriseEntity;
import com.ccit.entity.PaymentEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.PaymentEnterpriseService;
import com.ccit.vo.PaymentEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class PaymentEnterpriseServiceImpl implements PaymentEnterpriseService {

    @Autowired
    private PaymentEnterpriseDao paymentEnterpriseDao;

    @Autowired
    private OrderEnterpriseDao orderEnterpriseDao;

    @Autowired
    private Assembler<PaymentEnterpriseBean, PaymentEnterpriseEntity, PaymentEnterpriseVo> paymentEnterpriseAssembler;


    @Override
    public List<PaymentEnterpriseVo> listPaymentByOrderId(long orderId) {
        List<PaymentEnterpriseEntity> paymentEntityList = paymentEnterpriseDao.findByOrderId(orderId);
        return paymentEnterpriseAssembler.toVos(paymentEntityList);
    }

    @Override
    public PaymentEnterpriseVo getPayment(long paymentId) {
        PaymentEnterpriseEntity paymentEntity = paymentEnterpriseDao.findOne(paymentId);
        Validator.notNull(paymentEntity, ResourceException.error(CustomerErrorConstants.PAYMENT_ENTERPRISE_NOT_EXIST));
        return paymentEnterpriseAssembler.toVo(paymentEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removePayment(long paymentId) {
        paymentEnterpriseDao.deleteById(paymentId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void createPayment(long orderId, PaymentEnterpriseBean paymentEnterpriseBean) {
        OrderEnterpriseEntity orderEntity = orderEnterpriseDao.findOne(orderId);
        Validator.notNull(orderEntity, ResourceException.error(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_EXIST));

        Calendar orderBillingTime = Calendar.getInstance();
        orderBillingTime.setTime(orderEntity.getBillingTime());

        Calendar paymentBillingTime = Calendar.getInstance();
        paymentBillingTime.setTime(paymentEnterpriseBean.getBillingDate());
        paymentBillingTime.set(Calendar.DAY_OF_MONTH, orderBillingTime.get(Calendar.DAY_OF_MONTH));
        paymentBillingTime.add(Calendar.DAY_OF_MONTH, -1);


        BigDecimal totalAmount = new BigDecimal(paymentEnterpriseBean.getAmount());
        BigDecimal amount = totalAmount.divide(BigDecimal.valueOf(paymentEnterpriseBean.getPaymentPeriod()), 0, BigDecimal.ROUND_FLOOR);
        for (int i = 0; i < paymentEnterpriseBean.getPaymentPeriod(); i++) {
            PaymentEnterpriseEntity paymentEntity = paymentEnterpriseAssembler.fromBean(paymentEnterpriseBean);
            paymentEntity.setOrderId(orderId);

            paymentBillingTime.add(Calendar.MONTH, 1);
            paymentEntity.setBillingDate(new Timestamp(paymentBillingTime.getTimeInMillis()));

            if (i == 0) {
                paymentEntity.setAmount(
                        totalAmount.subtract(
                                amount.multiply(
                                        BigDecimal.valueOf(paymentEnterpriseBean.getPaymentPeriod() - 1)
                                )
                        )
                );
            } else {
                paymentEntity.setAmount(amount);
            }
            paymentEnterpriseDao.saveOrUpdate(paymentEntity);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyPayment(long paymentId, PaymentEnterpriseBean paymentEnterpriseBean) {
        PaymentEnterpriseEntity paymentEntity = paymentEnterpriseDao.findOne(paymentId);
        Validator.notNull(paymentEntity, ResourceException.error(CustomerErrorConstants.PAYMENT_ENTERPRISE_NOT_EXIST));
        paymentEntity.setAmount(new BigDecimal(paymentEnterpriseBean.getAmount()));
        paymentEntity.setPaymentDate(paymentEnterpriseBean.getPaymentDate());
        paymentEntity.setOperator(paymentEnterpriseBean.getOperator());
        paymentEnterpriseDao.saveOrUpdate(paymentEntity);
    }

}
