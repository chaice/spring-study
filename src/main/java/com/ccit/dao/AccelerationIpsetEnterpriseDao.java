package com.ccit.dao;

import com.ccit.entity.AccelerationIpsetEnterpriseEntity;

import java.util.List;

public interface AccelerationIpsetEnterpriseDao extends BaseDao<AccelerationIpsetEnterpriseEntity, Long> {

    void deleteByCollectionId(long collectionId);

    List<AccelerationIpsetEnterpriseEntity> findByCollectionId(Long collectionId);

    AccelerationIpsetEnterpriseEntity findByNameCollectionId(String ipsetName, Long collectionId);
}
