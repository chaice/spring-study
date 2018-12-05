package com.ccit.dao.impl;

import com.ccit.entity.AdnCustomerDomainEntity;
import com.ccit.dao.AdnCustomerDomainDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnCustomerDomainDaoImpl extends BaseDaoImpl<AdnCustomerDomainEntity, Long> implements AdnCustomerDomainDao{

    @Override
    public List<AdnCustomerDomainEntity> findByCustomerId(long customerId) {
        String hql = "FROM AdnCustomerDomainEntity WHERE customerId=:customerId ";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", customerId);
        return query.list();
    }

    @Override
    public AdnCustomerDomainEntity findByCustomerDomainId(long id) {
        String hql = "FROM AdnCustomerDomainEntity WHERE customerId=:customerId AND id=:id";
        Query query = currentSession().createQuery(hql);
        query.setLong("id", id);
        List<AdnCustomerDomainEntity> adnCustomerDomainEntities = query.list();
        if (adnCustomerDomainEntities != null && !adnCustomerDomainEntities.isEmpty()) {
            return adnCustomerDomainEntities.get(0);
        } else {
            return null;
        }
    }

}
