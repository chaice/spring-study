package com.ccit.rest.sextant.request;


import com.ccit.entity.AccelerationDomainEnterpriseEntity;

public class DomainReq {

    private long id;

    private long ipsetId;

    private String domain;

    private long collectionId;

    public DomainReq(AccelerationDomainEnterpriseEntity domainEntity) {
        this.id = domainEntity.getId();
        this.domain = domainEntity.getDomain();
        this.collectionId = domainEntity.getCollectionId();
        this.ipsetId = domainEntity.getIpsetId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(long ipsetId) {
        this.ipsetId = ipsetId;
    }
}
