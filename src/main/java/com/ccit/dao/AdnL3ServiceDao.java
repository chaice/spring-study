package com.ccit.dao;

import com.ccit.entity.AdnL3ServiceEntity;

public interface AdnL3ServiceDao extends BaseDao<AdnL3ServiceEntity, Long> {

    void deleteByCustomerId(long id);
}
