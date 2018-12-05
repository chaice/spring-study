package com.ccit.enums;

public enum OrderEnterpriseStatus {

    WHOLESALER_CHECKING("wholesaler_checking"),

    WHOLESALER_REJECTED("wholesaler_rejected"),

    MANUFACTURER_CHECKING("manufacturer_checking"),

    MANUFACTURER_REJECTED("manufacturer_rejected"),

    ACTIVATED("activated"),

    EXPIRED("expired");

    private String name;

    OrderEnterpriseStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
