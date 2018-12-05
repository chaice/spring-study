package com.ccit.dao;

import com.ccit.entity.EntryEnterpriseL2tpEntity;

import java.util.List;

public interface EntryEnterpriseL2tpDao extends BaseDao<EntryEnterpriseL2tpEntity, Long> {

    /**
     * 搜索加速入口
     *
     * @return
     */
    List<EntryEnterpriseL2tpEntity> searchEntry();


    /**
     * 根据ip和port统计
     *
     * @param masterIP
     * @return
     */
    int countByMasterIP(String masterIP);


    /**
     * 根据name统计
     *
     * @param name
     * @return
     */
    int countByName(String name);


}