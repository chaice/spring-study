package com.ccit.dao;

import com.ccit.entity.AdnHttpDomainEntity;

import java.util.List;

public interface AdnHttpDomainDao extends BaseDao<AdnHttpDomainEntity, Long> {

    List<AdnHttpDomainEntity> findByServiceId(long id);

    void deleteByServiceId(long serviceId);

    List<AdnHttpDomainEntity> findBySourceIp(String sourceIp);
}
