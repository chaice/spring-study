package com.ccit.vo;

public class AccelerationDomainEnterpriseVo {
    private long id;
    private Long ipsetId;
    private Long collectionId;
    private String domain;
    private String createTime;
    private long sequence;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(Long ipsetId) {
        this.ipsetId = ipsetId;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}
