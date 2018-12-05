package com.ccit.service;

import com.ccit.vo.AdnServiceZoneVo;

import java.util.List;

public interface AdnServiceZoneService {

    List<AdnServiceZoneVo> getServiceZone(long serviceId, String serviceType);
}