package com.ccit.vo;


/**
 * Created by lance on 7/11/17.
 */
public class OrderEnterpriseVo {

    private long id;

    private String orderId;

    private String province;

    private String city;

    private String address;

    private String enterpriseName;

    private String contact;

    private String contactPhone;

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

    private int paymentPeriod;

    private String paymentType;

    private long collectionDay;

    private String pubIp;

    private String pubGateway;
    //子网掩码
    private String netmask;

    private Integer machineNumber;

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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
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

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public long getCollectionDay() {
        return collectionDay;
    }

    public void setCollectionDay(long collectionDay) {
        this.collectionDay = collectionDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }
}
