package com.ccit.dao;

import com.ccit.entity.CustomerEnterpriseEntity;

import java.util.List;

public interface CustomerEnterpriseDao extends BaseDao<CustomerEnterpriseEntity, Long> {


    /**
     * 搜索客户
     * @param name
     * @param portalName
     * @param sort
     * @return
     */
    List<CustomerEnterpriseEntity> searchCustomerEnterprise(String name, String portalName, String sort);


    /**
     * 根据name查询
     * @param name
     * @return
     */
    CustomerEnterpriseEntity getByName(String name);


    /**
     * 根据sextantName查询
     * @param sextantName
     * @return
     */
    CustomerEnterpriseEntity getBySextantName(String sextantName);
}
