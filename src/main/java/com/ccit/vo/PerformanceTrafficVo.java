package com.ccit.vo;

import java.sql.Timestamp;

public class PerformanceTrafficVo {

    private long throughputTX;

    private long throughputRX;

    private long bandwidthTX;

    private long bandwidthRX;

    private Timestamp samplingTime;

    public long getThroughputTX() {
        return throughputTX;
    }

    public void setThroughputTX(long throughputTX) {
        this.throughputTX = throughputTX;
    }

    public long getThroughputRX() {
        return throughputRX;
    }

    public void setThroughputRX(long throughputRX) {
        this.throughputRX = throughputRX;
    }

    public long getBandwidthTX() {
        return bandwidthTX;
    }

    public void setBandwidthTX(long bandwidthTX) {
        this.bandwidthTX = bandwidthTX;
    }

    public long getBandwidthRX() {
        return bandwidthRX;
    }

    public void setBandwidthRX(long bandwidthRX) {
        this.bandwidthRX = bandwidthRX;
    }

    public Timestamp getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(Timestamp samplingTime) {
        this.samplingTime = samplingTime;
    }
}
