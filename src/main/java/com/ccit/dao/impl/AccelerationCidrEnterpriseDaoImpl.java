package com.ccit.dao.impl;

import com.ccit.entity.AccelerationCidrEnterpriseEntity;
import com.ccit.web.interceptor.UserInterceptor;
import com.ccit.dao.AccelerationCidrEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccelerationCidrEnterpriseDaoImpl extends BaseDaoImpl<AccelerationCidrEnterpriseEntity, Long> implements AccelerationCidrEnterpriseDao {

    @Override
    public List<AccelerationCidrEnterpriseEntity> findByIpsetId(long ipsetId) {
        String hql = "FROM AccelerationCidrEnterpriseEntity WHERE ipsetId=:ipsetId ORDER BY SUBSTRING_INDEX(cidr,'/',-1)+0 DESC";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        return query.list();
    }

    @Override
    public void deleteByCollectionId(long collectionId) {
        String hql = "DELETE FROM AccelerationCidrEnterpriseEntity WHERE collectionId=:collectionId";
        Query query = currentSession().createQuery(hql);
        query.setLong("collectionId", collectionId);
        query.executeUpdate();
    }

    @Override
    public void deleteByIpsetId(long ipsetId) {
        String hql = "DELETE FROM AccelerationCidrEnterpriseEntity WHERE ipsetId=:ipsetId";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        query.executeUpdate();
    }

    @Override
    public AccelerationCidrEnterpriseEntity findByCidrIpsetId(String cidr, long ipsetId) {
        String hql = "FROM AccelerationCidrEnterpriseEntity WHERE ipsetId=:ipsetId AND cidr=:cidr";
        Query query = currentSession().createQuery(hql);
        query.setLong("ipsetId", ipsetId);
        query.setString("cidr", cidr);
        query.setCacheable(true);
        query.setCacheMode(UserInterceptor.getLocalSecondLevelCache().get());
        query.setCacheRegion("AccelerationCidrEnterpriseDao.findByCidrIpsetId");

        List<AccelerationCidrEnterpriseEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AccelerationCidrEnterpriseEntity> findByCountryIsNull(int limit) {
        String hql = "FROM AccelerationCidrEnterpriseEntity WHERE country IS NULL";
        Query query = currentSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(limit);
        return query.list();
    }
}
