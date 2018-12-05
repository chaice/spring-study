package com.ccit.dao;

import com.ccit.entity.AgencyInternetCafeEntity;

public interface AgencyInternetCafeDao extends BaseDao<AgencyInternetCafeEntity, Long> {

    /**
     * 根据account查询
     * @param name
     * @return
     */
    AgencyInternetCafeEntity getByAccount(String name);

}
