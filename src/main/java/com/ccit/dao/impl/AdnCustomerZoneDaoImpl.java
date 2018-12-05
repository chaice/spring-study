package com.ccit.dao.impl;

import com.ccit.entity.AdnCustomerZoneEntity;
import com.ccit.dao.AdnCustomerZoneDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnCustomerZoneDaoImpl extends BaseDaoImpl<AdnCustomerZoneEntity, Long> implements AdnCustomerZoneDao {

    @Override
    public List<AdnCustomerZoneEntity> findByCustomerId(Long customerId) {
        String hql = "FROM AdnCustomerZoneEntity WHERE customerId=:customerId ";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", customerId);
        return query.list();
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        String hql = "DELETE FROM AdnCustomerZoneEntity WHERE customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", customerId);
        query.executeUpdate();
    }

    @Override
    public List<AdnCustomerZoneEntity> findByZoneId(long zoneId) {
        String hql = "FROM AdnCustomerZoneEntity WHERE zoneId=:zoneId ";
        Query query = currentSession().createQuery(hql);
        query.setLong("zoneId", zoneId);
        return query.list();
    }
}
