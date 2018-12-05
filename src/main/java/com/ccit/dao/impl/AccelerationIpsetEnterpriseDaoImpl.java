package com.ccit.dao.impl;

import com.ccit.entity.AccelerationIpsetEnterpriseEntity;
import com.ccit.web.interceptor.UserInterceptor;
import com.ccit.dao.AccelerationIpsetEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccelerationIpsetEnterpriseDaoImpl extends BaseDaoImpl<AccelerationIpsetEnterpriseEntity, Long> implements AccelerationIpsetEnterpriseDao {

    @Override
    public void deleteByCollectionId(long collectionId) {
        String hql = "DELETE FROM AccelerationIpsetEnterpriseEntity WHERE collectionId=:collectionId";
        Query query = currentSession().createQuery(hql);
        query.setLong("collectionId", collectionId);
        query.executeUpdate();
    }

    @Override
    public List<AccelerationIpsetEnterpriseEntity> findByCollectionId(Long collectionId) {
        String hql = "FROM AccelerationIpsetEnterpriseEntity WHERE collectionId=:collectionId ORDER BY sequence desc ";
        Query query = currentSession().createQuery(hql);
        query.setLong("collectionId", collectionId);
        query.setCacheable(true);
        query.setCacheMode(UserInterceptor.getLocalSecondLevelCache().get());
        query.setCacheRegion("AccelerationIpsetEnterpriseDao.findByCollectionId");

        return query.list();
    }

    @Override
    public AccelerationIpsetEnterpriseEntity findByNameCollectionId(String ipsetName, Long collectionId) {
        String hql = "FROM AccelerationIpsetEnterpriseEntity WHERE ipsetName=:ipsetName AND collectionId=:collectionId";
        Query query = currentSession().createQuery(hql);
        query.setLong("collectionId", collectionId);
        query.setString("ipsetName", ipsetName);
        List<AccelerationIpsetEnterpriseEntity> list = query.list();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

}
