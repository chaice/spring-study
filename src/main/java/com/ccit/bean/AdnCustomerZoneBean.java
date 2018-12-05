package com.ccit.bean;

import java.util.List;

public class AdnCustomerZoneBean {

    private long id;

    private Long customerId;

    private List<Long> zoneList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<Long> zoneList) {
        this.zoneList = zoneList;
    }
}
