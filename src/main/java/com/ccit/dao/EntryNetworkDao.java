package com.ccit.dao;

import com.ccit.entity.EntryNetworkEntity;

import java.util.List;

public interface EntryNetworkDao extends BaseDao<EntryNetworkEntity, Long> {

    /**
     * 搜索加速入口
     *
     * @return
     */
    List<EntryNetworkEntity> searchEntry();


    /**
     * 根据ip和port统计
     *
     * @param masterIP
     * @return
     */
    int countByMasterIPSlaveIP(String masterIP, String slaveIP);


    /**
     * 根据name统计
     *
     * @param name
     * @return
     */
    int countByName(String name);

}