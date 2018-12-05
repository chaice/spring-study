package com.ccit.dao;

import com.ccit.entity.AccelerationDomainEnterpriseEntity;

import java.util.List;

public interface AccelerationDomainEnterpriseDao extends BaseDao<AccelerationDomainEnterpriseEntity, Long> {

    List<AccelerationDomainEnterpriseEntity> findByIpsetId(long ipsetId);

    void deleteByCollectonId(long collectionId);

    void deleteByIpsetId(long ipsetId);

    AccelerationDomainEnterpriseEntity findByDomainIpsetId(String domain, long ipsetId);

}
