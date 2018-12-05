package com.ccit.vo;

import com.ccit.entity.AdnServiceZoneEntity;

public class AdnServiceZoneVo {

    private long id;

    private Long serviceId;

    private String serviceType;

    private Long zoneId;

    private Long customerId;

    private AdnZoneVo adnZone;

    public AdnServiceZoneVo( ) {
    }

    public AdnServiceZoneVo(AdnServiceZoneEntity entity) {
        this.id = entity.getZoneId();
        this.serviceId = entity.getServiceId();
        this.serviceType = entity.getServiceType();
        this.zoneId = entity.getZoneId();
        this.customerId = entity.getCustomerId();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public AdnZoneVo getAdnZone() {
        return adnZone;
    }

    public void setAdnZone(AdnZoneVo adnZone) {
        this.adnZone = adnZone;
    }
}
