package com.ccit.rest.rainbow.request;

import com.ccit.entity.PerformanceAdnEntryTrafficEntity;

import java.sql.Timestamp;

public class PerformanceTrafficReq {

    private long id;

    private Long serviceEntryId;

    private Long serviceZoneId;

    private Long serviceId;

    private Long entryId;

    private Long zoneId;

    private Long customerId;

    private Long serviceHttpDomainId;

    private String serviceType;

    //采样时间
    private Timestamp samplingTime;

    //发送流量（绝对值）
    private Long absoluteThroughputTx;

    //接收流量（绝对值）
    private Long absoluteThroughputRx;

    //发送流量（差值）
    private Long differenceThroughputTx;

    //接收流量（差值）
    private Long differenceThroughputRx;

    //发送带宽
    private Long bandwidthTx;

    //接收带宽
    private Long bandwidthRx;

    public PerformanceTrafficReq() {
    }

    public PerformanceTrafficReq(PerformanceAdnEntryTrafficEntity performanceAdnEntryTrafficEntity) {

        this.id = performanceAdnEntryTrafficEntity.getId();
        this.serviceEntryId = performanceAdnEntryTrafficEntity.getServiceEntryId();
        this.serviceZoneId = performanceAdnEntryTrafficEntity.getServiceZoneId();
        this.serviceId = performanceAdnEntryTrafficEntity.getServiceId();
        this.entryId = performanceAdnEntryTrafficEntity.getEntryId();
        this.zoneId = performanceAdnEntryTrafficEntity.getZoneId();
        this.customerId = performanceAdnEntryTrafficEntity.getCustomerId();
        if (performanceAdnEntryTrafficEntity.getServiceHttpDomainId() != null) {
            this.serviceHttpDomainId = performanceAdnEntryTrafficEntity.getServiceHttpDomainId();
        }
        this.serviceType = performanceAdnEntryTrafficEntity.getServiceType();
        this.samplingTime = performanceAdnEntryTrafficEntity.getSamplingTime();
        this.absoluteThroughputTx = performanceAdnEntryTrafficEntity.getAbsoluteThroughputTx();
        this.absoluteThroughputRx = performanceAdnEntryTrafficEntity.getAbsoluteThroughputRx();
        this.differenceThroughputTx = performanceAdnEntryTrafficEntity.getDifferenceThroughputTx();
        this.differenceThroughputRx = performanceAdnEntryTrafficEntity.getDifferenceThroughputRx();
        this.bandwidthTx = performanceAdnEntryTrafficEntity.getBandwidthTx();
        this.bandwidthRx = performanceAdnEntryTrafficEntity.getBandwidthRx();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getServiceEntryId() {
        return serviceEntryId;
    }

    public void setServiceEntryId(Long serviceEntryId) {
        this.serviceEntryId = serviceEntryId;
    }

    public Long getServiceZoneId() {
        return serviceZoneId;
    }

    public void setServiceZoneId(Long serviceZoneId) {
        this.serviceZoneId = serviceZoneId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getServiceHttpDomainId() {
        return serviceHttpDomainId;
    }

    public void setServiceHttpDomainId(Long serviceHttpDomainId) {
        this.serviceHttpDomainId = serviceHttpDomainId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp samplingTime) {
        this.samplingTime = samplingTime;
    }

    public Long getAbsoluteThroughputTx() {
        return absoluteThroughputTx;
    }

    public void setAbsoluteThroughputTx(Long absoluteThroughputTx) {
        this.absoluteThroughputTx = absoluteThroughputTx;
    }

    public Long getAbsoluteThroughputRx() {
        return absoluteThroughputRx;
    }

    public void setAbsoluteThroughputRx(Long absoluteThroughputRx) {
        this.absoluteThroughputRx = absoluteThroughputRx;
    }

    public Long getDifferenceThroughputTx() {
        return differenceThroughputTx;
    }

    public void setDifferenceThroughputTx(Long differenceThroughputTx) {
        this.differenceThroughputTx = differenceThroughputTx;
    }

    public Long getDifferenceThroughputRx() {
        return differenceThroughputRx;
    }

    public void setDifferenceThroughputRx(Long differenceThroughputRx) {
        this.differenceThroughputRx = differenceThroughputRx;
    }

    public Long getBandwidthTx() {
        return bandwidthTx;
    }

    public void setBandwidthTx(Long bandwidthTx) {
        this.bandwidthTx = bandwidthTx;
    }

    public Long getBandwidthRx() {
        return bandwidthRx;
    }

    public void setBandwidthRx(Long bandwidthRx) {
        this.bandwidthRx = bandwidthRx;
    }
}
