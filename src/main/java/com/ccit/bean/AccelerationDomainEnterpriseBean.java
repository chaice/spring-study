package com.ccit.bean;


public class AccelerationDomainEnterpriseBean {

    private Long id;

    private Long collectionId;

    private Long ipsetId;

    private String domain;

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(Long ipsetId) {
        this.ipsetId = ipsetId;
    }
}
