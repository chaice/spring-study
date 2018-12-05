package com.ccit.bean;


public class AdnCustomerDomainBean {

    private long id;

    private Long customerId;

    private String secondLevelDomain;


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


    public String getSecondLevelDomain() {
        return secondLevelDomain;
    }

    public void setSecondLevelDomain(String secondLevelDomain) {
        this.secondLevelDomain = secondLevelDomain;
    }
}
