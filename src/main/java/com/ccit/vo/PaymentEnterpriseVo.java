package com.ccit.vo;


/**
 * Created by lance on 7/11/17.
 */
public class PaymentEnterpriseVo {

    private long id;
    private long orderId;
    private String billingDate;
    private String amount;
    private String paymentDate;
    private String operator;
    private String recordTime;

    public long getId() {
        return id;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getBillingDate() {
        return billingDate;
    }

    public String getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getOperator() {
        return operator;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setBillingDate(String billingDate) {
        this.billingDate = billingDate;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
