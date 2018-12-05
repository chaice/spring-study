package com.ccit.dao.impl;

import com.ccit.entity.AdnHttpDomainEntity;
import com.ccit.dao.AdnHttpDomainDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdnHttpDomainDaoImpl extends BaseDaoImpl<AdnHttpDomainEntity, Long> implements AdnHttpDomainDao {

    @Override
    public List<AdnHttpDomainEntity> findByServiceId(long serviceId) {
        String hql = "FROM AdnHttpDomainEntity WHERE serviceId = :serviceId";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        return query.list();
    }

    @Override
    public void deleteByServiceId(long serviceId) {
        String hql = "DELETE FROM AdnHttpDomainEntity WHERE serviceId = :serviceId";
        Query query = currentSession().createQuery(hql);
        query.setLong("serviceId", serviceId);
        query.executeUpdate();
    }

    @Override
    public List<AdnHttpDomainEntity> findBySourceIp(String sourceIp) {
        String hql = "FROM AdnHttpDomainEntity WHERE sourceIp = :sourceIp";
        Query query = currentSession().createQuery(hql);
        query.setString("sourceIp", sourceIp);

        return query.list();
    }
}
