package com.ccit.dao.impl;

import com.ccit.entity.AccelerationCollectionEnterpriseEntity;
import com.ccit.dao.AccelerationCollectionEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AccelerationCollectionEnterpriseDaoImpl extends BaseDaoImpl<AccelerationCollectionEnterpriseEntity, Long> implements AccelerationCollectionEnterpriseDao {

    @Override
    public AccelerationCollectionEnterpriseEntity findByCollectionNameAndAccelerateMode(String collectionName, String accelerateMode) {
        String hql = "FROM AccelerationCollectionEnterpriseEntity WHERE collectionName = :collectionName AND accelerateMode = :accelerateMode";
        Query query = currentSession().createQuery(hql);
        query.setString("collectionName", collectionName);
        query.setString("accelerateMode",accelerateMode);
        return (AccelerationCollectionEnterpriseEntity) query.uniqueResult();
    }

    @Override
    public List<AccelerationCollectionEnterpriseEntity> findByCollectionTypeAndAccelerateMode(String type, String accelerateMode) {
        String hql = "FROM AccelerationCollectionEnterpriseEntity WHERE type = :type AND accelerateMode = :accelerateMode";
        Query query = currentSession().createQuery(hql);
        query.setString("type", type);
        query.setString("accelerateMode", accelerateMode);
        return query.list();
    }

    @Override
    public List<AccelerationCollectionEnterpriseEntity> findByAccelerateMode(String accelerateMode) {
        String hql = "FROM AccelerationCollectionEnterpriseEntity WHERE accelerateMode = :accelerateMode";
        Query query = currentSession().createQuery(hql);
        query.setString("accelerateMode", accelerateMode);
        return query.list();
    }
}
