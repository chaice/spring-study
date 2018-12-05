package com.ccit.service;

import com.ccit.vo.AdnServiceEntryVo;

import java.util.List;

public interface AdnServiceEntryService {

    List<AdnServiceEntryVo> getServiceEntry(long serviceId, String serviceType, long zoneId);
}