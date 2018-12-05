package com.ccit.vo;

public class PerformanceEntryThroughputVo {
    private long throughputTotal;                           //总流量

    public PerformanceEntryThroughputVo(long throughputTotal) {
        this.throughputTotal = throughputTotal;
    }


    public long getThroughputTotal() {
        return throughputTotal;
    }

    public void setThroughputTotal(long throughputTotal) {
        this.throughputTotal = throughputTotal;
    }
}
