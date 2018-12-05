package com.ccit.rest.axis.request;

import com.ccit.entity.NetworkBoxUpgradePackageEntity;

import java.sql.Timestamp;

public class BoxUpgradePackageReq {

    private long id;

    private String packageVersion;

    private String suitableVersion;

    private String fileName;

    private String fileMD5;

    private Timestamp createTime;

    private String downloadUserName;

    private String downloadPassword;

    private String downloadUrl;

    public BoxUpgradePackageReq(NetworkBoxUpgradePackageEntity entity) {
        this.id = entity.getId();
        this.packageVersion = entity.getPackageVersion();
        this.suitableVersion = entity.getSuitableVersion();
        this.fileMD5 = entity.getFileMd5();
        this.fileName = entity.getFileName();
        this.downloadUserName = entity.getDownloadUserName();
        this.downloadPassword = entity.getDownloadPassword();
        this.createTime = entity.getCreateTime();
        this.downloadUrl = entity.getDownloadUrl();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public String getSuitableVersion() {
        return suitableVersion;
    }

    public void setSuitableVersion(String suitableVersion) {
        this.suitableVersion = suitableVersion;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDownloadUserName() {
        return downloadUserName;
    }

    public void setDownloadUserName(String downloadUserName) {
        this.downloadUserName = downloadUserName;
    }

    public String getDownloadPassword() {
        return downloadPassword;
    }

    public void setDownloadPassword(String downloadPassword) {
        this.downloadPassword = downloadPassword;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
