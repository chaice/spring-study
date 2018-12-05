package com.ccit.dao;

import com.ccit.entity.AdnCustomerDomainEntity;

import java.util.List;

public interface AdnCustomerDomainDao extends BaseDao<AdnCustomerDomainEntity, Long> {

    List<AdnCustomerDomainEntity> findByCustomerId(long customerId);

    AdnCustomerDomainEntity findByCustomerDomainId(long id);
}
