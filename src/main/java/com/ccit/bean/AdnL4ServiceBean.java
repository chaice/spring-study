package com.ccit.bean;

import java.util.List;

public class AdnL4ServiceBean {

    private long id;
    //服务名称
    private String serviceName;
    //源域名
    private String originalDomain;
    //协议
    private String transportProtocol;
    //源IP
    private String sourceIp;
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

    public String getOriginalDomain() {
        return originalDomain;
    }

    public void setOriginalDomain(String originalDomain) {
        this.originalDomain = originalDomain;
    }

    public String getTransportProtocol() {
        return transportProtocol;
    }

    public void setTransportProtocol(String transportProtocol) {
        this.transportProtocol = transportProtocol;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
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
