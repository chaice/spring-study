package com.ccit.vo;

import com.ccit.entity.AdnHttpDomainEntity;

public class AdnHttpDomainVo {

    private long id;

    private Long serviceId;

    private String secondLevelDomain;

    private String sourceIp;

    private Long customerId;

    public AdnHttpDomainVo() {
    }

    public AdnHttpDomainVo(AdnHttpDomainEntity adnHttpDomainEntity) {
        this.id = adnHttpDomainEntity.getId();
        this.serviceId = adnHttpDomainEntity.getServiceId();
        this.secondLevelDomain = adnHttpDomainEntity.getSecondLevelDomain();
        this.sourceIp = adnHttpDomainEntity.getSourceIp();
        this.customerId = adnHttpDomainEntity.getCustomerId();
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

    public String getSecondLevelDomain() {
        return secondLevelDomain;
    }

    public void setSecondLevelDomain(String secondLevelDomain) {
        this.secondLevelDomain = secondLevelDomain;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
