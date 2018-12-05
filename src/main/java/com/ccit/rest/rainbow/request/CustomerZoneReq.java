package com.ccit.rest.rainbow.request;

import com.ccit.bean.AdnCustomerZoneBean;

import java.util.List;

public class CustomerZoneReq {

    private long id;

    private Long customerId;

    private List<Long> zoneList;

    public CustomerZoneReq(AdnCustomerZoneBean adnCustomerZoneBean) {
        this.id = adnCustomerZoneBean.getId();
        this.customerId = adnCustomerZoneBean.getCustomerId();
        this.zoneList = adnCustomerZoneBean.getZoneList();
    }

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
