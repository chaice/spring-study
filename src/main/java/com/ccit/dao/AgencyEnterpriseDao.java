package com.ccit.dao;

import com.ccit.entity.AgencyEnterpriseEntity;

public interface AgencyEnterpriseDao extends BaseDao<AgencyEnterpriseEntity, Long> {

    /**
     * 根据account查询
     * @param name
     * @return
     */
    AgencyEnterpriseEntity getByAccount(String name);

}
