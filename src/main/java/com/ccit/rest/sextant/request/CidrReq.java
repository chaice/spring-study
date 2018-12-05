package com.ccit.rest.sextant.request;

import com.ccit.entity.AccelerationCidrEnterpriseEntity;

public class CidrReq {

    private long id;
    private long ipsetId;
    private long collectionId;
    private String cidr;

    public CidrReq(AccelerationCidrEnterpriseEntity cidrEntity) {
        this.id = cidrEntity.getId();
        this.collectionId = cidrEntity.getCollectionId();
        this.ipsetId = cidrEntity.getIpsetId();
        this.cidr = cidrEntity.getCidr();
    }

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
