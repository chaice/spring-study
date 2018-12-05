package com.ccit.dao;

import com.ccit.entity.BoxEntryIPIPEntity;

import java.util.List;

public interface BoxEntryIPIPDao extends BaseDao<BoxEntryIPIPEntity, String> {


    /**
     * 根据boxId统计
     * @param boxId
     * @return
     */
    int countByBoxId(long boxId);


    /**
     * 根据boxId查询
     * @param boxId
     * @return
     */
    List<BoxEntryIPIPEntity> listByBoxId(long boxId);


    /**
     * 根据boxId删除
     * @param boxId
     * @return
     */
    int deleteByBoxId(long boxId);

    int deleteByEntryId(long entryId);

}