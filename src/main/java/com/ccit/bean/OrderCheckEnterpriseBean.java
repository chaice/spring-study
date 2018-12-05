package com.ccit.bean;

import java.sql.Timestamp;

public class OrderCheckEnterpriseBean {

    private long id;

    private Timestamp openTime;

    private Timestamp billingTime;

    private Long customerId;

    private String disposeComment;

    private String orderStatus;

    private String paymentType;

    private int paymentPeriod;

    private String address;

    private String enterpriseName;

    private Integer machineNumber;

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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }
}