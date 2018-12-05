package com.ccit.dao;

import com.ccit.entity.AdnCustomerZoneEntity;

import java.util.List;

public interface AdnCustomerZoneDao extends BaseDao<AdnCustomerZoneEntity, Long> {

    List<AdnCustomerZoneEntity> findByCustomerId(Long customerId);

    void deleteByCustomerId(Long customerId);

    List<AdnCustomerZoneEntity> findByZoneId(long zoneId);
}
