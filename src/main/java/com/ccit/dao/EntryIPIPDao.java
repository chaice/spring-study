package com.ccit.dao;


import com.ccit.entity.EntryIPIPEntity;

import java.util.List;

public interface EntryIPIPDao extends BaseDao<EntryIPIPEntity, Long> {

    /**
     * 搜索加速入口
     * @return
     */
    List<EntryIPIPEntity> searchEntry();


    /**
     * 根据ip和port统计
     * @param masterIP
     * @return
     */
    int countByMasterIP(String masterIP);


    /**
     * 根据name统计
     * @param name
     * @return
     */
    int countByName(String name);



}