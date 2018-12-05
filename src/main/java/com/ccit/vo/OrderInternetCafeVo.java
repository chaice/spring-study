package com.ccit.vo;


/**
 * Created by lance on 7/11/17.
 */
public class OrderInternetCafeVo {

    private long id;

    private String orderId;

    private String province;

    private String city;

    private String internetCafeName;

    private String contact;

    private String contactPhone;

    private String pubIp;

    private String pubGateway;

    private Integer bandwidth;

    private String orderStatus;

    private String disposeComment;

    private String manufacturerTime;

    private String wholesalerTime;

    private String openTime;

    private String billingTime;

    private String wholesalerName;

    private String retailerName;

    private String customerName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getInternetCafeName() {
        return internetCafeName;
    }

    public void setInternetCafeName(String internetCafeName) {
        this.internetCafeName = internetCafeName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getPubIp() {
        return pubIp;
    }

    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    public String getPubGateway() {
        return pubGateway;
    }

    public void setPubGateway(String pubGateway) {
        this.pubGateway = pubGateway;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDisposeComment() {
        return disposeComment;
    }

    public void setDisposeComment(String disposeComment) {
        this.disposeComment = disposeComment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getManufacturerTime() {
        return manufacturerTime;
    }

    public void setManufacturerTime(String manufacturerTime) {
        this.manufacturerTime = manufacturerTime;
    }

    public String getWholesalerTime() {
        return wholesalerTime;
    }

    public void setWholesalerTime(String wholesalerTime) {
        this.wholesalerTime = wholesalerTime;
    }

    public String getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(String billingTime) {
        this.billingTime = billingTime;
    }

    public String getWholesalerName() {
        return wholesalerName;
    }

    public void setWholesalerName(String wholesalerName) {
        this.wholesalerName = wholesalerName;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
