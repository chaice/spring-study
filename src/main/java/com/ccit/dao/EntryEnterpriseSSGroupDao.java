package com.ccit.dao;

import com.ccit.entity.EntryEnterpriseSSGroupEntity;

import java.util.List;

public interface EntryEnterpriseSSGroupDao extends BaseDao<EntryEnterpriseSSGroupEntity, Long> {

    /**
     * 根据入口ID查是否重复
     *
     * @param commonMasterId
     * @param commonSlaveId
     * @param specialMasterId
     * @param specialSlaveId
     * @return
     */
    int countByEntryId(Long commonMasterId, Long commonSlaveId, Long specialMasterId, Long specialSlaveId);

    /**
     * 根据name统计
     *
     * @param name
     * @return
     */
    int countByName(String name);

    /**
     * @param entryId
     */
    int deleteByEntryId(long entryId);

    /**
     * 根据入口ID查询入口组信息
     * @param entryId
     */
    List<EntryEnterpriseSSGroupEntity> findByEntryId(long entryId);
}