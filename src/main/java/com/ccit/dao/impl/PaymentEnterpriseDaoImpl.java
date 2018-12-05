package com.ccit.dao.impl;

import com.ccit.entity.PaymentEnterpriseEntity;
import com.ccit.dao.PaymentEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentEnterpriseDaoImpl extends BaseDaoImpl<PaymentEnterpriseEntity, Long> implements PaymentEnterpriseDao {

    @Override
    public List<PaymentEnterpriseEntity> findByOrderId(long orderId) {
        String hql = "from PaymentEnterpriseEntity where orderId=:orderId order by billingDate desc ";
        Query query = currentSession().createQuery(hql).setLong("orderId", orderId);
        return query.list();
    }

    @Override
    public PaymentEnterpriseEntity findLastOneByOrderId(long orderId) {
        String hql = "from PaymentEnterpriseEntity where orderId=:orderId order by billingDate desc ";
        Query query = currentSession().createQuery(hql).setLong("orderId", orderId);
        List<PaymentEnterpriseEntity> paymentEnterpriseEntityList = query.list();
        if(paymentEnterpriseEntityList != null && !paymentEnterpriseEntityList.isEmpty()){
            return paymentEnterpriseEntityList.get(0);
        }else{
            return null;
        }
    }

}
