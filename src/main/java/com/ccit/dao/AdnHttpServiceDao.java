package com.ccit.dao;

import com.ccit.entity.AdnHttpServiceEntity;

public interface AdnHttpServiceDao extends BaseDao<AdnHttpServiceEntity, Long> {

    void deleteByCustomerId(long id);

    AdnHttpServiceEntity findBySourcePortAndCustomerId(Integer sourcePort, Long customerId);
}
