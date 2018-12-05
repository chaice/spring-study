package com.ccit.bean;

import java.sql.Timestamp;

public class AccelerationBoxUpgradePackageBean {

    private long id;

    private String packageVersion;

    private String suitableVersion;

    private String fileName;

    private String fileMd5;

    private Timestamp createTime;

    private String downloadUserName;

    private String downloadPassword;

    private String downloadUrl;

    private String tmpFileName;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getTmpFileName() {
        return tmpFileName;
    }

    public void setTmpFileName(String tmpFileName) {
        this.tmpFileName = tmpFileName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
