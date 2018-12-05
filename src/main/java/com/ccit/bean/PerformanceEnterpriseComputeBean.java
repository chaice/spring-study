package com.ccit.bean;

import java.sql.Timestamp;

public class PerformanceEnterpriseComputeBean {

    private long id;                                //主键ID
    private Long boxId;                             //小盒子ID
    private Integer cpuUsageGlobal;                 //CPU占用率（%）
    private Long memoryCapacity;                    //内存总量（Byte）
    private Long memoryUsed;                        //内存使用量（Byte）
    private Timestamp samplingTime;                 //采样时间

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

    public Integer getCpuUsageGlobal() {
        return cpuUsageGlobal;
    }

    public void setCpuUsageGlobal(Integer cpuUsageGlobal) {
        this.cpuUsageGlobal = cpuUsageGlobal;
    }

    public Long getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(Long memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp samplingTime) {
        this.samplingTime = samplingTime;
    }
}
