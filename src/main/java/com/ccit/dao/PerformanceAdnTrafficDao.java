package com.ccit.dao;

import com.ccit.bean.PerformanceQueryBean;
import com.ccit.entity.PerformanceAdnEntryTrafficEntity;

import java.sql.Timestamp;
import java.util.List;

public interface PerformanceAdnTrafficDao extends BaseDao<PerformanceAdnEntryTrafficEntity, Long> {

    List<PerformanceAdnEntryTrafficEntity> findTrafficByCustomerZone(long zoneId, PerformanceQueryBean performanceQueryBean);

    List<PerformanceAdnEntryTrafficEntity> findTrafficByCustomerId(long customerId, PerformanceQueryBean performanceQueryBean);

    PerformanceAdnEntryTrafficEntity findByServiceEntryIdSamplingTime(long serviceEntryId, Timestamp samplingTime);

    List<PerformanceAdnEntryTrafficEntity> findTrafficEntry(long entryId, PerformanceQueryBean performanceQueryBean);

    void deleteByServiceIdAndServiceType(long serviceId, String serviceType);

    List<PerformanceAdnEntryTrafficEntity> findTrafficByService(long serviceId, String serviceType, PerformanceQueryBean performanceQueryBean);

    List<PerformanceAdnEntryTrafficEntity> findTrafficByServiceZone(long serviceId, long zoneId, String serviceType, PerformanceQueryBean performanceQueryBean);

    List<PerformanceAdnEntryTrafficEntity> findTrafficByServiceEntry(long serviceId, long zoneId, long entryId, String serviceType, PerformanceQueryBean performanceQueryBean);
}
