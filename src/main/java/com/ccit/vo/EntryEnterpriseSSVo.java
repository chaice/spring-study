package com.ccit.vo;

public class EntryEnterpriseSSVo {

    private Long id;

    private String name;

    private String ip;

    private Integer port;

    private String dns;

    private String password;

    public EntryEnterpriseSSVo() {
    }

    public EntryEnterpriseSSVo(Long id, String name, String ip, Integer port, String dns, String password) {
        this.id = id;
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.dns = dns;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}