package com.ccit.vo;

public class EntryEnterpriseSSGroupVo {

    private Long id;

    private String name;

    private String description;

    private EntryEnterpriseSSVo commonMasterSSVo;

    private EntryEnterpriseSSVo commonSlaveSSVo;

    private EntryEnterpriseSSVo specialMasterSSVo;

    private EntryEnterpriseSSVo specialSlaveSSVo;

    private Boolean needSync;

    public EntryEnterpriseSSGroupVo() {
    }

    public EntryEnterpriseSSGroupVo(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public EntryEnterpriseSSGroupVo(Long id, String name, String description, EntryEnterpriseSSVo commonMasterSSVo, EntryEnterpriseSSVo commonSlaveSSVo, EntryEnterpriseSSVo specialMasterSSVo, EntryEnterpriseSSVo specialSlaveSSVo, Boolean needSync) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.commonMasterSSVo = commonMasterSSVo;
        this.commonSlaveSSVo = commonSlaveSSVo;
        this.specialMasterSSVo = specialMasterSSVo;
        this.specialSlaveSSVo = specialSlaveSSVo;
        this.needSync = needSync;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntryEnterpriseSSVo getCommonMasterSSVo() {
        return commonMasterSSVo;
    }

    public void setCommonMasterSSVo(EntryEnterpriseSSVo commonMasterSSVo) {
        this.commonMasterSSVo = commonMasterSSVo;
    }

    public EntryEnterpriseSSVo getCommonSlaveSSVo() {
        return commonSlaveSSVo;
    }

    public void setCommonSlaveSSVo(EntryEnterpriseSSVo commonSlaveSSVo) {
        this.commonSlaveSSVo = commonSlaveSSVo;
    }

    public EntryEnterpriseSSVo getSpecialMasterSSVo() {
        return specialMasterSSVo;
    }

    public void setSpecialMasterSSVo(EntryEnterpriseSSVo specialMasterSSVo) {
        this.specialMasterSSVo = specialMasterSSVo;
    }

    public EntryEnterpriseSSVo getSpecialSlaveSSVo() {
        return specialSlaveSSVo;
    }

    public void setSpecialSlaveSSVo(EntryEnterpriseSSVo specialSlaveSSVo) {
        this.specialSlaveSSVo = specialSlaveSSVo;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }
}