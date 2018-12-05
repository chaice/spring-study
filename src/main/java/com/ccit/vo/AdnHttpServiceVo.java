package com.ccit.vo;

import java.util.List;

public class AdnHttpServiceVo {

    private long id;
    //服务名称
    private String serviceName;
    //服务类型
    private String serviceType;
    //源域名
    private String originalDomain;
    //Http服务关联的三级域名
    private List<AdnHttpDomainVo> adnHttpDomainVoList;
    //源端口
    private Integer sourcePort;
    //服务域名
    private String serviceDomain;
    //用户ID
    private Long customerId;
    //服务状态
    private String serviceStatus;

    private List<AdnZoneVo> adnZoneVoList;
    //所属客户
    private CustomerEnterpriseVo customer;

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

    public List<AdnHttpDomainVo> getAdnHttpDomainVoList() {
        return adnHttpDomainVoList;
    }

    public void setAdnHttpDomainVoList(List<AdnHttpDomainVo> adnHttpDomainVoList) {
        this.adnHttpDomainVoList = adnHttpDomainVoList;
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

    public List<AdnZoneVo> getAdnZoneVoList() {
        return adnZoneVoList;
    }

    public void setAdnZoneVoList(List<AdnZoneVo> adnZoneVoList) {
        this.adnZoneVoList = adnZoneVoList;
    }

    public CustomerEnterpriseVo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEnterpriseVo customer) {
        this.customer = customer;
    }
}
