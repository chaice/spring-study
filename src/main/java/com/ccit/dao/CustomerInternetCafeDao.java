package com.ccit.dao;

import com.ccit.entity.CustomerInternetCafeEntity;

import java.util.List;

public interface CustomerInternetCafeDao extends BaseDao<CustomerInternetCafeEntity, Long> {


    /**
     * 搜索客户
     * @param name
     * @param portalName
     * @param sort
     * @return
     */
    List<CustomerInternetCafeEntity> searchCustomerInternetCafe(String name, String portalName, String sort);


    /**
     * 根据name查询
     * @param name
     * @return
     */
    CustomerInternetCafeEntity getByName(String name);


    /**
     * 根据zionName查询
     * @param zionName
     * @return
     */
    CustomerInternetCafeEntity getByZionName(String zionName);
}
