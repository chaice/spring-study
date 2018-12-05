package com.ccit.entity;

import com.ccit.bean.PerformanceEnterpriseComputeBean;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "performance_enterprise_compute", schema = "matrix", catalog = "")
public class PerformanceEnterpriseComputeEntity {

    private long id;                                //主键ID
    private Long boxId;                             //小盒子ID
    private Integer cpuUsageGlobal;                 //CPU占用率（%）
    private Long memoryCapacity;                    //内存总量（Byte）
    private Long memoryUsed;                        //内存使用量（Byte）
    private Timestamp samplingTime;                 //采样时间

    public PerformanceEnterpriseComputeEntity(){

    }

    public PerformanceEnterpriseComputeEntity(PerformanceEnterpriseComputeBean computeBean){
        this.id = computeBean.getId();
        this.boxId = computeBean.getBoxId();
        this.cpuUsageGlobal = computeBean.getCpuUsageGlobal();
        this.memoryCapacity = computeBean.getMemoryCapacity();
        this.memoryUsed = computeBean.getMemoryUsed();
        this.samplingTime = computeBean.getSamplingTime();
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
    @Column(name = "cpu_usage_global")
    public Integer getCpuUsageGlobal() {
        return cpuUsageGlobal;
    }

    public void setCpuUsageGlobal(Integer cpuUsageGlobal) {
        this.cpuUsageGlobal = cpuUsageGlobal;
    }

    @Basic
    @Column(name = "memory_capacity")
    public Long getMemoryCapacity() {
        return memoryCapacity;
    }

    public void setMemoryCapacity(Long memoryCapacity) {
        this.memoryCapacity = memoryCapacity;
    }

    @Basic
    @Column(name = "memory_used")
    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    @Basic
    @Column(name = "sampling_time")
    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp simplingTime) {
        this.samplingTime = simplingTime;
    }
}

