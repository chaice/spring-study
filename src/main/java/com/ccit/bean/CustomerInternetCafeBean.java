package com.ccit.bean;

public class CustomerInternetCafeBean {

    private Long id;

    private String name;

    // 客户信息
    private String info;

    private String province;

    private String city;

    private String contact;

    private String contactPhone;

    // Zion关联用户名
    private String zionName;

    private String zionPassword;

    private Long createUid;

    // 最后一次修改人ID
    private Long lastUid;


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

    public String getZionName() {
        return zionName;
    }

    public void setZionName(String zionName) {
        this.zionName = zionName;
    }

    public String getZionPassword() {
        return zionPassword;
    }

    public void setZionPassword(String zionPassword) {
        this.zionPassword = zionPassword;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Long getLastUid() {
        return lastUid;
    }

    public void setLastUid(Long lastUid) {
        this.lastUid = lastUid;
    }
}