package com.ccit.bean;

import java.util.List;

public class PerformanceBean {

    private String controlIp;

    private List<PerformanceAdnTrafficBean> performanceTrafficList;
    //采样时间
    private long samplingTime;

    public String getControlIp() {
        return controlIp;
    }

    public void setControlIp(String controlIp) {
        this.controlIp = controlIp;
    }

    public List<PerformanceAdnTrafficBean> getPerformanceTrafficList() {
        return performanceTrafficList;
    }

    public void setPerformanceTrafficList(List<PerformanceAdnTrafficBean> performanceTrafficList) {
        this.performanceTrafficList = performanceTrafficList;
    }

    public long getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(long samplingTime) {
        this.samplingTime = samplingTime;
    }
}
