package com.ccit.bean;

import java.sql.Timestamp;

public class PerformanceEnterpriseTrafficBean {

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
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
