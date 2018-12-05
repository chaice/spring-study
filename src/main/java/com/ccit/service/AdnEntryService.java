package com.ccit.service;

import com.ccit.bean.AdnEntryBean;
import com.ccit.vo.AdnEntryVo;

import java.util.List;

public interface AdnEntryService {

    List<AdnEntryVo> listEntry();

    AdnEntryVo getEntry(long entryId);

    /**
     * 创建云链入口
     *
     * @param adnEntryBean
     */
    void createEntry(AdnEntryBean adnEntryBean);

    /**
     * 修改云链入口
     *
     * @param adnEntryBean
     */
    void modifyEntry(AdnEntryBean adnEntryBean);

    /**
     * 删除云链入口
     *
     * @param entryId
     */
    void removeEntry(long entryId);

    List<AdnEntryVo> listUsableEntry(String entryType, long serviceId, long zoneId);
}