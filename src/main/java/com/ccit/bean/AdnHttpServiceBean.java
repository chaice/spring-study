package com.ccit.bean;

import java.util.List;

public class AdnHttpServiceBean {

    private long id;
    //服务名称
    private String serviceName;

    private String serviceType;
    //源域名
    private String originalDomain;
    //
    private List<AdnHttpDomainBean> adnServiceHttpDomainReqList;
    //源端口
    private Integer sourcePort;
    //服务域名
    private String serviceDomain;
    //用户ID
    private Long customerId;
    //服务状态
    private String serviceStatus;
    //服务关联的区域
    private List<Long> zoneList;

    private List<Long> entryIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOriginalDomain() {
        return originalDomain;
    }

    public void setOriginalDomain(String originalDomain) {
        this.originalDomain = originalDomain;
    }

    public List<AdnHttpDomainBean> getAdnServiceHttpDomainReqList() {
        return adnServiceHttpDomainReqList;
    }

    public void setAdnServiceHttpDomainReqList(List<AdnHttpDomainBean> adnServiceHttpDomainReqList) {
        this.adnServiceHttpDomainReqList = adnServiceHttpDomainReqList;
    }

    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(String serviceDomain) {
        this.serviceDomain = serviceDomain;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public List<Long> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Long> zoneList) {
        this.zoneList = zoneList;
    }

    public List<Long> getEntryIds() {
        return entryIds;
    }

    public void setEntryIds(List<Long> entryIds) {
        this.entryIds = entryIds;
    }
}
