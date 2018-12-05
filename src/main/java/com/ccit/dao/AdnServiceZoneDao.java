package com.ccit.dao;

import com.ccit.entity.AdnServiceZoneEntity;

import java.util.List;

public interface AdnServiceZoneDao extends BaseDao<AdnServiceZoneEntity, Long> {

    List<AdnServiceZoneEntity> findByServiceIdAndServiceType(long serviceId, String serviceType);

    void deleteByServiceIdAndServiceType(long serviceId, String serviceType);

    AdnServiceZoneEntity findByServiceIdAndZoneIdAndServiceType(long serviceId, Long zoneId, String serviceType);

    AdnServiceZoneEntity findByZoneId(long zoneId);

    AdnServiceZoneEntity findByCustomerZoneId(Long customerId, Long zoneId);

    List<AdnServiceZoneEntity> findByCustomerId(Long customerId);

    void deleteByCustomerId(long id);

    List<AdnServiceZoneEntity> findByServiceId(long serviceId, String serviceType);
}
