package com.ccit.dao.impl;

import com.ccit.entity.AdnL3ServiceEntity;
import com.ccit.dao.AdnL3ServiceDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AdnL3ServiceDaoImpl extends BaseDaoImpl<AdnL3ServiceEntity, Long> implements AdnL3ServiceDao {

    @Override
    public void deleteByCustomerId(long id) {
        String hql = "DELETE FROM AdnL3ServiceEntity WHERE customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", id);
        query.executeUpdate();
    }
}
