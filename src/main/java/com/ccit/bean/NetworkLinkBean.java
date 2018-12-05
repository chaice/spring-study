package com.ccit.bean;

import java.sql.Timestamp;

public class NetworkLinkBean {

    private long id;

    private long boxIdLeft;

    private String connectIpLeft;

    private long boxIdRight;

    private String connectIpRight;

    private String alias;

    private Long userId;

    private Long bandwidth;

    private Integer latency;

    private Integer lossRate;

    private Timestamp heartbeatTime;

    private String status;

    private Boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBoxIdLeft() {
        return boxIdLeft;
    }

    public void setBoxIdLeft(long boxIdLeft) {
        this.boxIdLeft = boxIdLeft;
    }

    public String getConnectIpLeft() {
        return connectIpLeft;
    }

    public void setConnectIpLeft(String connectIpLeft) {
        this.connectIpLeft = connectIpLeft;
    }

    public long getBoxIdRight() {
        return boxIdRight;
    }

    public void setBoxIdRight(long boxIdRight) {
        this.boxIdRight = boxIdRight;
    }

    public String getConnectIpRight() {
        return connectIpRight;
    }

    public void setConnectIpRight(String connectIpRight) {
        this.connectIpRight = connectIpRight;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Long bandwidth) {
        this.bandwidth = bandwidth;
    }

    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    public Integer getLossRate() {
        return lossRate;
    }

    public void setLossRate(Integer lossRate) {
        this.lossRate = lossRate;
    }

    public Timestamp getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(Timestamp heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
