package com.ccit.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "acceleration_box_upgrade_package", schema = "matrix", catalog = "")
public class AccelerationBoxUpgradePackageEntity {

    private long id;

    private String packageVersion;

    private String suitableVersion;

    private String fileName;

    private String fileMd5;

    private Timestamp createTime;

    private String downloadUserName;

    private String downloadPassword;

    private String downloadUrl;

    public AccelerationBoxUpgradePackageEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "package_version")
    public String getPackageVersion() {
        return packageVersion;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    @Basic
    @Column(name = "suitable_version")
    public String getSuitableVersion() {
        return suitableVersion;
    }

    public void setSuitableVersion(String suitableVersion) {
        this.suitableVersion = suitableVersion;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_md5")
    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "download_user_name")
    public String getDownloadUserName() {
        return downloadUserName;
    }

    public void setDownloadUserName(String downloadUserName) {
        this.downloadUserName = downloadUserName;
    }

    @Basic
    @Column(name = "download_password")
    public String getDownloadPassword() {
        return downloadPassword;
    }

    public void setDownloadPassword(String downloadPassword) {
        this.downloadPassword = downloadPassword;
    }

    @Basic
    @Column(name = "download_url")
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
