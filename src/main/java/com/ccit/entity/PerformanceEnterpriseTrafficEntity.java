package com.ccit.entity;

import com.ccit.bean.PerformanceEnterpriseTrafficBean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "performance_enterprise_traffic", schema = "matrix", catalog = "")
public class PerformanceEnterpriseTrafficEntity {

    private long id;                                        //主键ID
    private Long boxId;                                     //小盒子ID
    private String interfaceName;                           //网口名称
    private String interfaceType;                           //网口类型：WAN|ACCELERATE
    private Timestamp samplingTime;                         //采样时间
    private Long absoluteThroughputTx;                      //发送流量（绝对值）
    private Long absoluteThroughputRx;                      //接收流量（绝对值）
    private Long differenceThroughputTx;                    //发送流量（差值）
    private Long differenceThroughputRx;                    //接收流量（差值）
    private Long bandwidthTx;                               //发送带宽
    private Long bandwidthRx;                               //接收带宽

    public PerformanceEnterpriseTrafficEntity(){

    }

    public PerformanceEnterpriseTrafficEntity(PerformanceEnterpriseTrafficBean trafficBean){
        this.id = trafficBean.getId();
        this.boxId = trafficBean.getBoxId();
        this.interfaceName = trafficBean.getInterfaceName();
        this.interfaceType = trafficBean.getInterfaceType();
        this.samplingTime = trafficBean.getSamplingTime();
        this.absoluteThroughputTx = trafficBean.getAbsoluteThroughputTx();
        this.absoluteThroughputRx = trafficBean.getAbsoluteThroughputRx();
        this.differenceThroughputTx = trafficBean.getDifferenceThroughputTx();
        this.differenceThroughputRx = trafficBean.getDifferenceThroughputRx();
        this.bandwidthTx = trafficBean.getBandwidthTx();
        this.bandwidthRx = trafficBean.getBandwidthRx();
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "box_id")
    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    @Basic
    @Column(name = "interface_name")
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Basic
    @Column(name = "interface_type")
    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
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

}
