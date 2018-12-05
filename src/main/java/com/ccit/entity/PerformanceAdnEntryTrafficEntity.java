package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "performance_adn_entry_traffic", schema = "matrix", catalog = "")
public class PerformanceAdnEntryTrafficEntity {

    private long id;

    private Long serviceEntryId;

    private Long serviceZoneId;

    private Long serviceId;

    private Long entryId;

    private Long zoneId;

    private Long customerId;

    private Long serviceHttpDomainId;

    private String serviceType;

    private Timestamp samplingTime;                         //采样时间

    private Long absoluteThroughputTx;                      //发送流量（绝对值）

    private Long absoluteThroughputRx;                      //接收流量（绝对值）

    private Long differenceThroughputTx;                    //发送流量（差值）

    private Long differenceThroughputRx;                    //接收流量（差值）

    private Long bandwidthTx;                               //发送带宽

    private Long bandwidthRx;                               //接收带宽

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "sampling_time")
    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp simplingTime) {
        this.samplingTime = simplingTime;
    }

    @Basic
    @Column(name = "absolute_throughput_tx")
    public Long getAbsoluteThroughputTx() {
        return absoluteThroughputTx;
    }

    public void setAbsoluteThroughputTx(Long absoluteThroughputTx) {
        this.absoluteThroughputTx = absoluteThroughputTx;
    }

    @Basic
    @Column(name = "absolute_throughput_rx")
    public Long getAbsoluteThroughputRx() {
        return absoluteThroughputRx;
    }

    public void setAbsoluteThroughputRx(Long absoluteThroughputRx) {
        this.absoluteThroughputRx = absoluteThroughputRx;
    }

    @Basic
    @Column(name = "difference_throughput_tx")
    public Long getDifferenceThroughputTx() {
        return differenceThroughputTx;
    }

    public void setDifferenceThroughputTx(Long differenceThroughputTx) {
        this.differenceThroughputTx = differenceThroughputTx;
    }

    @Basic
    @Column(name = "difference_throughput_rx")
    public Long getDifferenceThroughputRx() {
        return differenceThroughputRx;
    }

    public void setDifferenceThroughputRx(Long differenceThroughputRx) {
        this.differenceThroughputRx = differenceThroughputRx;
    }

    @Basic
    @Column(name = "bandwidth_tx")
    public Long getBandwidthTx() {
        return bandwidthTx;
    }

    public void setBandwidthTx(Long bandwidthTx) {
        this.bandwidthTx = bandwidthTx;
    }

    @Basic
    @Column(name = "bandwidth_rx")
    public Long getBandwidthRx() {
        return bandwidthRx;
    }

    public void setBandwidthRx(Long bandwidthRx) {
        this.bandwidthRx = bandwidthRx;
    }


    @Basic
    @Column(name = "service_entry_id")
    public Long getServiceEntryId() {
        return serviceEntryId;
    }

    public void setServiceEntryId(Long serviceEntryId) {
        this.serviceEntryId = serviceEntryId;
    }

    @Basic
    @Column(name = "service_zone_id")
    public Long getServiceZoneId() {
        return serviceZoneId;
    }

    public void setServiceZoneId(Long serviceZoneId) {
        this.serviceZoneId = serviceZoneId;
    }

    @Basic
    @Column(name = "service_id")
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Basic
    @Column(name = "entry_id")
    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    @Basic
    @Column(name = "zone_id")
    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
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
    @Column(name = "service_http_domain_id")
    public Long getServiceHttpDomainId() {
        return serviceHttpDomainId;
    }

    public void setServiceHttpDomainId(Long serviceHttpDomainId) {
        this.serviceHttpDomainId = serviceHttpDomainId;
    }

    @Basic
    @Column(name = "service_type")
    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
