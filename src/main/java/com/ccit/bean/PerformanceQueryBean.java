package com.ccit.bean;


import java.util.Date;
import java.util.TimeZone;

public class PerformanceQueryBean {

    private Long beginTime;                                 //开始时间
    private Long endTime;                                   //结束时间
    private int interval;                                   //采样间隔


    public static final TimeZone tz = TimeZone.getDefault();

    public Date getBeginTime() {
        if (beginTime == null) {
            return null;
        } else {
            return new Date(beginTime);
        }
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        if (endTime == null) {
            return null;
        } else {
            return new Date(endTime);
        }
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
