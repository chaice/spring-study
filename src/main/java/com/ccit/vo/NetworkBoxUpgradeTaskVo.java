package com.ccit.vo;

import java.sql.Timestamp;

public class NetworkBoxUpgradeTaskVo {

    private long id;

    private String status;

    private String taskType;

    private Timestamp startTime;

    private Timestamp endTime;

    private BoxNetworkVo boxNetworkVo;

    private NetworkBoxUpgradePackageVo packageVo;

    public NetworkBoxUpgradeTaskVo(){

    }

    public NetworkBoxUpgradeTaskVo(long id, String status, String taskType, Timestamp startTime, Timestamp endTime) {
        this.id = id;
        this.status = status;
        this.taskType = taskType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public BoxNetworkVo getBoxNetworkVo() {
        return boxNetworkVo;
    }

    public void setBoxNetworkVo(BoxNetworkVo boxNetworkVo) {
        this.boxNetworkVo = boxNetworkVo;
    }

    public NetworkBoxUpgradePackageVo getPackageVo() {
        return packageVo;
    }

    public void setPackageVo(NetworkBoxUpgradePackageVo packageVo) {
        this.packageVo = packageVo;
    }
}
