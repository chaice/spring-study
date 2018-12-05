package com.ccit.bean;

import java.util.List;

public class NetworkBoxUpgradeTaskBean {

    private long id;

    private List<Long> boxIdList;

    private Long packageId;

    private String taskType;

    private long startTime;

    private long endTime;

    private String status;

    private String version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Long> getBoxIdList() {
        return boxIdList;
    }

    public void setBoxIdList(List<Long> boxIdList) {
        this.boxIdList = boxIdList;
    }

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
