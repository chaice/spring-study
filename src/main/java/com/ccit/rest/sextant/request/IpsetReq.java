package com.ccit.rest.sextant.request;

import com.ccit.entity.AccelerationCidrEnterpriseEntity;
import com.ccit.entity.AccelerationDomainEnterpriseEntity;
import com.ccit.entity.AccelerationIpsetEnterpriseEntity;

import java.util.LinkedList;
import java.util.List;

public class IpsetReq {

    private long id;
    private long collectionId;
    private String transportProtocol;
    private String port;
    private String ipsetName;
    private Boolean enable;
    private long sequence;
    private String accelerateMode;

    private List<CidrReq> cidrList = new LinkedList<>();
    private List<DomainReq> domainList = new LinkedList<>();

    public IpsetReq(AccelerationIpsetEnterpriseEntity ipsetEntity, List<AccelerationCidrEnterpriseEntity> cidrEntityList, List<AccelerationDomainEnterpriseEntity> domainEntityList) {
        this.id = ipsetEntity.getId();
        this.collectionId = ipsetEntity.getCollectionId();
        this.transportProtocol = ipsetEntity.getTransportProtocol();
        this.port = ipsetEntity.getPortTotal();
        this.sequence = ipsetEntity.getSequence();
        this.enable = ipsetEntity.getEnable();
        this.ipsetName = ipsetEntity.getIpsetName();
        this.accelerateMode = ipsetEntity.getAccelerateMode();
        for (AccelerationCidrEnterpriseEntity cidrEntity : cidrEntityList) {
            cidrList.add(new CidrReq(cidrEntity));
        }
        for (AccelerationDomainEnterpriseEntity domainEntity : domainEntityList) {
            domainList.add(new DomainReq(domainEntity));
        }
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

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIpsetName() {
        return ipsetName;
    }

    public void setIpsetName(String ipsetName) {
        this.ipsetName = ipsetName;
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

    public List<CidrReq> getCidrList() {
        return cidrList;
    }

    public void setCidrList(List<CidrReq> cidrList) {
        this.cidrList = cidrList;
    }

    public List<DomainReq> getDomainList() {
        return domainList;
    }

    public void setDomainList(List<DomainReq> domainList) {
        this.domainList = domainList;
    }

    public String getAccelerateMode() {
        return accelerateMode;
    }

    public void setAccelerateMode(String accelerateMode) {
        this.accelerateMode = accelerateMode;
    }
}
