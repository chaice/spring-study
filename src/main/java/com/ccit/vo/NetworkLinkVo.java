package com.ccit.vo;

import java.sql.Timestamp;

public class NetworkLinkVo {

    private long id;

    private BoxNetworkVo leftBox;

    private BoxNetworkVo rightBox;

    private String leftConnectIp;

    private String rightConnectIp;

    private String alias;

    private CustomerEnterpriseVo customer;

    private Long bandwidth;

    private Integer latency;

    private Integer lossRate;

    private Timestamp createTime;

    private String status;

    private Boolean enable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BoxNetworkVo getLeftBox() {
        return leftBox;
    }

    public void setLeftBox(BoxNetworkVo leftBox) {
        this.leftBox = leftBox;
    }

    public BoxNetworkVo getRightBox() {
        return rightBox;
    }

    public void setRightBox(BoxNetworkVo rightBox) {
        this.rightBox = rightBox;
    }

    public String getLeftConnectIp() {
        return leftConnectIp;
    }

    public void setLeftConnectIp(String leftConnectIp) {
        this.leftConnectIp = leftConnectIp;
    }

    public String getRightConnectIp() {
        return rightConnectIp;
    }

    public void setRightConnectIp(String rightConnectIp) {
        this.rightConnectIp = rightConnectIp;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public CustomerEnterpriseVo getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEnterpriseVo customer) {
        this.customer = customer;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
