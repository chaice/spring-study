package com.ccit.bean;

import java.sql.Timestamp;

public class OrderCheckInternetCafeBean {

    private long id;

    private Timestamp openTime;

    private Timestamp billingTime;

    private Long customerId;

    private String disposeComment;

    private String orderStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    public Timestamp getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(Timestamp billingTime) {
        this.billingTime = billingTime;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getDisposeComment() {
        return disposeComment;
    }

    public void setDisposeComment(String disposeComment) {
        this.disposeComment = disposeComment;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}