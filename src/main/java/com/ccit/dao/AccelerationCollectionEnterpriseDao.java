package com.ccit.dao;

import com.ccit.entity.AccelerationCollectionEnterpriseEntity;

import java.util.List;

public interface AccelerationCollectionEnterpriseDao extends BaseDao<AccelerationCollectionEnterpriseEntity, Long> {

    AccelerationCollectionEnterpriseEntity findByCollectionNameAndAccelerateMode(String collectionName, String accelerateMode);

    List<AccelerationCollectionEnterpriseEntity> findByCollectionTypeAndAccelerateMode(String type, String accelerateMode);

    List<AccelerationCollectionEnterpriseEntity> findByAccelerateMode(String accelerateMode);
}
