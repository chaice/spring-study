package com.ccit.enums;

public enum AdnServiceStatus {

    WAIT_SUBMIT("wait_submit"),

    WAIT_CHECKING("wait_checking"),

    IP_WAIT_CHECKING("ip_wait_checking"),

    ACTIVATED("activated");

    private String status;

    AdnServiceStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
