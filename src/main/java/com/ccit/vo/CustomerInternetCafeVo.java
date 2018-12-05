package com.ccit.vo;

public class CustomerInternetCafeVo {

    private Long id;

    private String name;

    // 客户信息
    private String info;

    private String province;

    private String city;

    // 联系人
    private String contact;

    private String contactPhone;

    private String email;

    // Zion Portal关联用户名
    private String zionName;

    public CustomerInternetCafeVo() {
    }

    public CustomerInternetCafeVo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZionName() {
        return zionName;
    }

    public void setZionName(String zionName) {
        this.zionName = zionName;
    }
}