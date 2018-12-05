package com.ccit.rest.axis.request;

import com.ccit.entity.NetworkBoxUpgradeTaskEntity;

import java.sql.Timestamp;

public class BoxUpgradeTaskReq {

    private long id;

    private Long boxId;

    private Long packageId;

    private Timestamp startTime;

    private Timestamp endTime;

    private String status;

    public BoxUpgradeTaskReq(){

    }

    public BoxUpgradeTaskReq(NetworkBoxUpgradeTaskEntity boxUpgradeTaskEntity){
        this.id = boxUpgradeTaskEntity.getId();
        this.boxId = boxUpgradeTaskEntity.getBoxId();
        this.packageId = boxUpgradeTaskEntity.getPackageId();
        this.startTime = boxUpgradeTaskEntity.getStartTime();
        this.endTime = boxUpgradeTaskEntity.getEndTime();
        this.status = boxUpgradeTaskEntity.getStatus();
    }

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

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
