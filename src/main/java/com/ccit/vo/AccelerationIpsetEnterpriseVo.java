package com.ccit.vo;

import java.util.List;

public class AccelerationIpsetEnterpriseVo {

    private long id;

    private Long collectionId;

    private String ipsetName;

    private String createTime;

    private String transportProtocol;

    private String portTotal;

    private Boolean enable;

    private long sequence;

    private String accelerateMode;

    private List<AccelerationCidrEnterpriseVo> cidrList;

    private List<AccelerationDomainEnterpriseVo> domainList;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getPortTotal() {
        return portTotal;
    }

    public void setPortTotal(String portTotal) {
        this.portTotal = portTotal;
    }

    public String getIpsetName() {
        return ipsetName;
    }

    public void setIpsetName(String ipsetName) {
        this.ipsetName = ipsetName;
    }

    public List<AccelerationCidrEnterpriseVo> getCidrList() {
        return cidrList;
    }

    public void setCidrList(List<AccelerationCidrEnterpriseVo> cidrList) {
        this.cidrList = cidrList;
    }

    public List<AccelerationDomainEnterpriseVo> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<AccelerationDomainEnterpriseVo> domainList) {
        this.domainList = domainList;
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
    }
}
