package com.ccit.dao;


import com.ccit.entity.GlobalDictionaryEntity;

public interface GlobalDictionaryDao extends BaseDao<GlobalDictionaryEntity, Long> {
    /**
     * 按key查询字典信息列表
     */
    GlobalDictionaryEntity findByKey(String key);

}
