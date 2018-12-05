package com.ccit.enums;

public enum OrderInternetCafeStatus {

    WHOLESALER_CHECKING("wholesaler_checking"),

    WHOLESALER_REJECTED("wholesaler_rejected"),

    MANUFACTURER_CHECKING("manufacturer_checking"),

    MANUFACTURER_REJECTED("manufacturer_rejected"),

    ACTIVATED("activated");

    private String name;

    OrderInternetCafeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
