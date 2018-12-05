package com.ccit.rest.rainbow.request;

public class Service3LayerReq {

    private long id;
    //服务名称
    private String serviceName;
    //源域名
    private String originalDomain;
    //源IP
    private String sourceIp;
    //用户ID
    private Long customerId;
    //服务状态
    private String serviceStatus;

    private String serviceDomain;

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

    public String getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(String serviceDomain) {
        this.serviceDomain = serviceDomain;
    }
}
