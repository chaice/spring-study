package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "adn_service_http", schema = "matrix", catalog = "")
public class AdnHttpServiceEntity {

    private long id;

    private String serviceName;

    private String serviceType;

    private String originalDomain;

    private Integer sourcePort;

    private String serviceDomain;

    private Long customerId;

    private String serviceStatus;

    private Timestamp createTime;

    private Timestamp effectiveTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "service_name")
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Basic
    @Column(name = "service_type")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    @Basic
    @Column(name = "original_domain")
    public String getOriginalDomain() {
        return originalDomain;
    }

    public void setOriginalDomain(String originalDomain) {
        this.originalDomain = originalDomain;
    }

    @Basic
    @Column(name = "source_port")
    public Integer getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(Integer sourcePort) {
        this.sourcePort = sourcePort;
    }

    @Basic
    @Column(name = "service_domain")
    public String getServiceDomain() {
        return serviceDomain;
    }

    public void setServiceDomain(String serviceDomain) {
        this.serviceDomain = serviceDomain;
    }

    @Basic
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "service_status")
    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "effective_time")
    public Timestamp getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Timestamp effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

}
