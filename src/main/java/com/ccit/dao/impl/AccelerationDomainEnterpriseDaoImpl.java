package com.ccit.dao.impl;

import com.ccit.entity.AccelerationDomainEnterpriseEntity;
import com.ccit.dao.AccelerationDomainEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccelerationDomainEnterpriseDaoImpl extends BaseDaoImpl<AccelerationDomainEnterpriseEntity, Long> implements AccelerationDomainEnterpriseDao {

    @Override
    public List<AccelerationDomainEnterpriseEntity> findByIpsetId(long ipsetId) {
        String hql = "FROM AccelerationDomainEnterpriseEntity WHERE ipsetId=:ipsetId";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        return query.list();
    }

    @Override
    public void deleteByCollectonId(long collectionId) {
        String hql = "DELETE FROM AccelerationDomainEnterpriseEntity WHERE collectionId=:collectionId";
        Query query = currentSession().createQuery(hql);
        query.setLong("collectionId", collectionId);
        query.executeUpdate();
    }

    @Override
    public void deleteByIpsetId(long ipsetId) {
        String hql = "DELETE FROM AccelerationDomainEnterpriseEntity WHERE ipsetId=:ipsetId";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        query.executeUpdate();
    }

    @Override
    public AccelerationDomainEnterpriseEntity findByDomainIpsetId(String domain, long ipsetId) {
        String hql = "FROM AccelerationDomainEnterpriseEntity WHERE ipsetId=:ipsetId AND domain=:domain";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        query.setString("domain", domain);
        List<AccelerationDomainEnterpriseEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}
