package com.ccit.enums;

public enum BoxUpgradeTaskStatus {

    WAIT_UPGRADE("wait_upgrade","待升级"),

    UPGRADING("upgrading","升级中"),

    UPGRADE_SUCCESS("upgrade_success","升级成功"),

    UPGRADE_FAIL("upgrade_failed","升级失败");

    private String status;

    private String description;

    BoxUpgradeTaskStatus(String status,String description){
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }
}
