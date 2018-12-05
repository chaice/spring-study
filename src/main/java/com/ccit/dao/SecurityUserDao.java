package com.ccit.dao;

import com.ccit.entity.SecurityUserEntity;

public interface SecurityUserDao extends BaseDao<SecurityUserEntity, Long> {


    /**
     * 根据name查询
     * @param name
     * @return
     */
    SecurityUserEntity findByName(String name);


}