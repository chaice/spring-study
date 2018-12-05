package com.ccit.vo;

import java.util.Date;

/**
 *  控制器
 */
public class ControllerVo {


    private Long ctlId;                                 //控制器编号
    private String ctlIp;                               //控制器IP
    private Short ctlPort;                              //控制器端口
    private Long createUid;                             //创建者编号
    private Date createTime;                            //创建时间
    private Long lastUid;                               //最后修改人编号
    private Date lastTime;                              //最后一次修改时间


    public Long getCtlId() {
        return ctlId;
    }

    public void setCtlId(Long ctlId) {
        this.ctlId = ctlId;
    }

    public String getCtlIp() {
        return ctlIp;
    }

    public void setCtlIp(String ctlIp) {
        this.ctlIp = ctlIp;
    }

    public Short getCtlPort() {
        return ctlPort;
    }

    public void setCtlPort(Short ctlPort) {
        this.ctlPort = ctlPort;
    }

    public Long getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Long createUid) {
        this.createUid = createUid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getLastUid() {
        return lastUid;
    }

    public void setLastUid(Long lastUid) {
        this.lastUid = lastUid;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }


}