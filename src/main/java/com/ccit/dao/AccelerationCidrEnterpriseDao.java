package com.ccit.dao;

import com.ccit.entity.AccelerationCidrEnterpriseEntity;

import java.util.List;

public interface AccelerationCidrEnterpriseDao extends BaseDao<AccelerationCidrEnterpriseEntity, Long> {

    List<AccelerationCidrEnterpriseEntity> findByIpsetId(long ipsetId);

    void deleteByCollectionId(long collectionId);

    void deleteByIpsetId(long ipsetId);

    AccelerationCidrEnterpriseEntity findByCidrIpsetId(String cidr, long ipsetId);

    List<AccelerationCidrEnterpriseEntity> findByCountryIsNull(int limit);
}
