package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lance on 7/11/17.
 */
@Entity
@Table(name = "order_enterprise", schema = "matrix", catalog = "")
public class OrderEnterpriseEntity {
    private long id;
    private String orderId;
    private String province;
    private String city;
    private String address;
    private String enterpriseName;
    private String contact;
    private String contactPhone;
    private Integer bandwidthUp;
    private Integer bandwidthDown;
    private Timestamp openTime;
    private String orderStatus;
    private Long retailerId;
    private Long wholesalerId;
    private String wholesalerComment;
    private Timestamp wholesalerTime;
    private Timestamp billingTime;
    private Timestamp manufacturerTime;
    private Long customerId;
    private String paymentType;
    private Integer paymentPeriod;
    private String pubIp;
    private String pubGateway;
    //子网掩码
    private String netmask;
    private Integer machineNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "enterprise_name")
    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String internetCafeName) {
        this.enterpriseName = internetCafeName;
    }

    @Basic
    @Column(name = "contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "contact_phone")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Basic
    @Column(name = "bandwidth_up")
    public Integer getBandwidthUp() {
        return bandwidthUp;
    }

    public void setBandwidthUp(Integer bandwidthUp) {
        this.bandwidthUp = bandwidthUp;
    }

    @Basic
    @Column(name = "bandwidth_down")
    public Integer getBandwidthDown() {
        return bandwidthDown;
    }

    public void setBandwidthDown(Integer bandwidthDown) {
        this.bandwidthDown = bandwidthDown;
    }

    @Basic
    @Column(name = "open_time")
    public Timestamp getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Timestamp openTime) {
        this.openTime = openTime;
    }

    @Basic
    @Column(name = "order_status")
    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Basic
    @Column(name = "retailer_id")
    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    @Basic
    @Column(name = "wholesaler_id")
    public Long getWholesalerId() {
        return wholesalerId;
    }

    public void setWholesalerId(Long wholesalerId) {
        this.wholesalerId = wholesalerId;
    }

    @Basic
    @Column(name = "wholesaler_comment")
    public String getWholesalerComment() {
        return wholesalerComment;
    }

    public void setWholesalerComment(String wholesalerComment) {
        this.wholesalerComment = wholesalerComment;
    }

    @Basic
    @Column(name = "wholesaler_time")
    public Timestamp getWholesalerTime() {
        return wholesalerTime;
    }

    public void setWholesalerTime(Timestamp wholesalerTime) {
        this.wholesalerTime = wholesalerTime;
    }

    @Basic
    @Column(name = "billing_time")
    public Timestamp getBillingTime() {
        return billingTime;
    }

    public void setBillingTime(Timestamp billingTime) {
        this.billingTime = billingTime;
    }

    @Basic
    @Column(name = "manufacturer_time")
    public Timestamp getManufacturerTime() {
        return manufacturerTime;
    }

    public void setManufacturerTime(Timestamp manufacturerTime) {
        this.manufacturerTime = manufacturerTime;
    }

    @Basic
    @Column(name = "customer_id")
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Basic
    @Column(name = "payment_type")
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Basic
    @Column(name = "payment_period")
    public Integer getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(Integer paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "pub_ip")
    public String getPubIp() {
        return pubIp;
    }

    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    @Basic
    @Column(name = "pub_gateway")
    public String getPubGateway() {
        return pubGateway;
    }

    public void setPubGateway(String pubGateway) {
        this.pubGateway = pubGateway;
    }

    @Basic
    @Column(name = "net_mask")
    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    @Basic
    @Column(name = "machine_number")
    public Integer getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(Integer machineNumber) {
        this.machineNumber = machineNumber;
    }
}
