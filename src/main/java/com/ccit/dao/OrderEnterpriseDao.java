package com.ccit.dao;

import com.ccit.entity.OrderEnterpriseEntity;

import java.util.List;

public interface OrderEnterpriseDao extends BaseDao<OrderEnterpriseEntity, Long> {

    List<OrderEnterpriseEntity> listByProvinceAndEnterpriseName(String province,String enterpriseName);
}
