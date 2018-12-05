package com.ccit.dao.impl;

import com.ccit.entity.AdnL4ServiceEntity;
import com.ccit.dao.AdnL4ServiceDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AdnL4ServiceDaoImpl extends BaseDaoImpl<AdnL4ServiceEntity, Long> implements AdnL4ServiceDao {

    @Override
    public void deleteByCustomerId(long id) {
        String hql = "DELETE FROM AdnL4ServiceEntity WHERE customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", id);
        query.executeUpdate();
    }
}
