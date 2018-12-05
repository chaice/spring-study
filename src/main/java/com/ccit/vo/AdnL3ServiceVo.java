package com.ccit.vo;

import java.util.List;

public class AdnL3ServiceVo {

    private long id;
    //服务名称
    private String serviceName;
    //源域名
    private String originalDomain;
    //源IP
    private String sourceIp;
    //服务域名
    private String serviceDomain;
    //用户ID
    private Long customerId;
    //服务状态
    private String serviceStatus;
    //所属客户
    private CustomerEnterpriseVo customer;

    private List<AdnZoneVo> adnZoneVoList;

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

    public String getOriginalDomain() {
        return originalDomain;
    }

    public void setOriginalDomain(String originalDomain) {
        this.originalDomain = originalDomain;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
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

    public CustomerEnterpriseVo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEnterpriseVo customer) {
        this.customer = customer;
    }

    public List<AdnZoneVo> getAdnZoneVoList() {
        return adnZoneVoList;
    }

    public void setAdnZoneVoList(List<AdnZoneVo> adnZoneVoList) {
        this.adnZoneVoList = adnZoneVoList;
    }
}
