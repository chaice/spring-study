package com.ccit.dao;

import com.ccit.entity.AdnL4ServiceEntity;

public interface AdnL4ServiceDao extends BaseDao<AdnL4ServiceEntity, Long> {

    void deleteByCustomerId(long id);
}
