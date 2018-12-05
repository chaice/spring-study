package com.ccit.dao;


import com.ccit.entity.EntryEnterpriseSSEntity;

import java.util.List;

public interface EntryEnterpriseSSDao extends BaseDao<EntryEnterpriseSSEntity, Long> {

    /**
     * 搜索加速入口
     *
     * @return
     */
    List<EntryEnterpriseSSEntity> searchEntry();

    /**
     * 根据ip和port统计
     *
     * @param ip
     * @param port
     * @return
     */
    int countByIpPort(String ip, int port);

    /**
     * 根据name统计
     *
     * @param name
     * @return
     */
    int countByName(String name);


}