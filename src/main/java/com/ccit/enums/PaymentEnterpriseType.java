package com.ccit.enums;

public enum PaymentEnterpriseType {

    PREPAID("prepaid"),

    POSTPAID("postpaid");

    private String name;

    PaymentEnterpriseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
