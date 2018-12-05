package com.ccit.dao;

import com.ccit.entity.AdnServiceEntryEntity;

import java.util.List;

public interface AdnServiceEntryDao extends BaseDao<AdnServiceEntryEntity, Long> {

    List<AdnServiceEntryEntity> findByEntryId(long entryId);

    List<AdnServiceEntryEntity> findByServiceTypeServiceIdZoneId(String serviceType, long serviceId, long zoneId);

    void deleteByServiceTypeServiceIdZoneId(String serviceType, long serviceId, long zoneId);

    AdnServiceEntryEntity findByServiceTypeEntryIdSourcePortTransportProtocol(String serviceType, long entryId, Integer sourcePort, String transportProtocol);

    List<AdnServiceEntryEntity> findByEntryIdSourcePort(long entryId, Integer sourcePort);

    AdnServiceEntryEntity findByServiceTypeServiceIdEntryId(String serviceType, Long serviceId, long entryId);

    List<AdnServiceEntryEntity> findByEntryIdSourcePortTransportProtocol(long entryId, Integer sourcePort, String transportProtocol);
}
