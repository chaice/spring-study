package com.ccit.vo;

public class PerformanceZoneThroughputVo {
    private long throughputTotal;                           //总流量
    private long throughputAccelerate;                      //加速流量


    public PerformanceZoneThroughputVo(long throughputTotal) {
        this.throughputTotal = throughputTotal;
    }

    public long getThroughputTotal() {
        return throughputTotal;
    }

    public long getThroughputAccelerate() {
        return throughputAccelerate;
    }
}
