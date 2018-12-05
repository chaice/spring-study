package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lance on 7/11/17.
 */
@Entity
@Table(name = "order_internet_cafe", schema = "matrix", catalog = "")
public class OrderInternetCafeEntity {
    private long id;
    private String orderId;
    private String province;
    private String city;
    private String internetCafeName;
    private String contact;
    private String contactPhone;
    private String pubIp;
    private String pubGateway;
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
    @Column(name = "internet_cafe_name")
    public String getInternetCafeName() {
        return internetCafeName;
    }

    public void setInternetCafeName(String internetCafeName) {
        this.internetCafeName = internetCafeName;
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

}
