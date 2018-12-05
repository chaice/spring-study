package com.ccit.dao;

import com.ccit.entity.AccelerationTargetEntity;

import java.sql.Timestamp;

public interface AccelerationTargetDao extends BaseDao<AccelerationTargetEntity, Long> {

    void deleteByExpired(Timestamp expiredTime);

    AccelerationTargetEntity findByCIDR(String cidr);

}
