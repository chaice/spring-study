package com.ccit.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "business_network", schema = "matrix", catalog = "")
public class NetworkLinkEntity {

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

    private Timestamp createTime;

    private String status;

    private Boolean enable;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "box_id_left")
    public long getBoxIdLeft() {
        return boxIdLeft;
    }

    public void setBoxIdLeft(long boxIdLeft) {
        this.boxIdLeft = boxIdLeft;
    }

    @Basic
    @Column(name = "connect_ip_left")
    public String getConnectIpLeft() {
        return connectIpLeft;
    }

    public void setConnectIpLeft(String connectIpLeft) {
        this.connectIpLeft = connectIpLeft;
    }

    @Basic
    @Column(name = "box_id_right")
    public long getBoxIdRight() {
        return boxIdRight;
    }

    public void setBoxIdRight(long boxIdRight) {
        this.boxIdRight = boxIdRight;
    }

    @Basic
    @Column(name = "connect_ip_right")
    public String getConnectIpRight() {
        return connectIpRight;
    }

    public void setConnectIpRight(String connectIpRight) {
        this.connectIpRight = connectIpRight;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "bandwidth")
    public Long getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Long bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Basic
    @Column(name = "latency")
    public Integer getLatency() {
        return latency;
    }

    public void setLatency(Integer latency) {
        this.latency = latency;
    }

    @Basic
    @Column(name = "loss_rate")
    public Integer getLossRate() {
        return lossRate;
    }

    public void setLossRate(Integer lossRate) {
        this.lossRate = lossRate;
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
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "enable")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
