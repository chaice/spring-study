package com.ccit.vo;

public class AccelerationCidrEnterpriseVo {

    private long id;

    private Long ipsetId;

    private Long collectionId;

    private String cidr;

    private String createTime;

    private String country;

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

    public String getCidr() {
        return cidr;
    }

    public void setCidr(String cidr) {
        this.cidr = cidr;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getIpsetId() {
        return ipsetId;
    }

    public void setIpsetId(Long ipsetId) {
        this.ipsetId = ipsetId;
    }
}
