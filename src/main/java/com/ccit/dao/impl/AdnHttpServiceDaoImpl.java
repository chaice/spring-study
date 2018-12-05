package com.ccit.dao.impl;

import com.ccit.entity.AdnHttpServiceEntity;
import com.ccit.dao.AdnHttpServiceDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnHttpServiceDaoImpl extends BaseDaoImpl<AdnHttpServiceEntity, Long> implements AdnHttpServiceDao {

    @Override
    public void deleteByCustomerId(long id) {
        String hql = "DELETE FROM AdnHttpServiceEntity WHERE customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setLong("customerId", id);
        query.executeUpdate();
    }

    @Override
    public AdnHttpServiceEntity findBySourcePortAndCustomerId(Integer sourcePort, Long customerId) {
        String hql = "FROM AdnHttpServiceEntity WHERE sourcePort = :sourcePort AND customerId=:customerId";
        Query query = currentSession().createQuery(hql);
        query.setInteger("sourcePort", sourcePort);
        query.setLong("customerId", customerId);

        List<AdnHttpServiceEntity> adnHttpServiceEntityList = query.list();
        if (adnHttpServiceEntityList != null && !adnHttpServiceEntityList.isEmpty()) {
            return adnHttpServiceEntityList.get(0);
        } else {
            return null;
        }
    }
}
