package com.ccit.dao;

import com.ccit.entity.BoxCollectionEntity;

import java.util.List;

public interface BoxCollectionDao extends BaseDao<BoxCollectionEntity, Long> {
    /**
     * 根据boxId查询
     * @param boxId
     * @return
     */
    List<BoxCollectionEntity> listByBoxId(long boxId);

    /**
     * 根据boxId删除
     * @param boxId
     * @return
     */
    int deleteByBoxId(long boxId);

    int deleteByCollectionId(long collectionId);
}
