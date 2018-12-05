package com.ccit.service;

import com.ccit.bean.PerformanceBean;
import com.ccit.bean.PerformanceQueryBean;
import com.ccit.vo.PerformanceTrafficVo;

import java.util.List;

public interface PerformanceAdnTrafficService {

    List<PerformanceTrafficVo> getCustomerBandwidth(long customerId, PerformanceQueryBean performanceQueryBean);

    //每个区域带宽
    List<PerformanceTrafficVo> getCurZoneBandwidth(long zoneId, PerformanceQueryBean performanceQueryBean);
    //每个入口带宽
    List<PerformanceTrafficVo> getZoneEntryBandwidth(long entryId, PerformanceQueryBean performanceQueryBean);

    void createPerformanceAdnTraffic(PerformanceBean performanceBean);

    List<PerformanceTrafficVo> getServiceTraffic(long serviceId, String serviceType, PerformanceQueryBean performanceQueryBean);

    List<PerformanceTrafficVo> getZoneTraffic(long serviceId, long zoneId, String serviceType, PerformanceQueryBean performanceQueryBean);

    List<PerformanceTrafficVo> getEntryTraffic(long serviceId, long zoneId, long entryId, String serviceType, PerformanceQueryBean performanceQueryBean);
}