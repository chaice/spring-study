package com.ccit.service;

import com.ccit.bean.AdnCustomerZoneBean;
import com.ccit.bean.AdnZoneBean;
import com.ccit.entity.AdnCustomerZoneEntity;
import com.ccit.vo.AdnZoneVo;

import java.util.List;

public interface AdnZoneService {

    List<AdnZoneVo> listZone();

    void addZone(AdnZoneBean adnZoneBean);

    AdnZoneVo getZone(long zoneId);

    void removeZone(long zoneId);

    void modifyZone(AdnZoneBean adnZoneBean);

    List<AdnCustomerZoneEntity> getCustomerZone(Long customerId);

    void modifyCustomerZone(AdnCustomerZoneBean adnCustomerZoneBean);

    List<AdnZoneVo> getZones(long customerId);
}