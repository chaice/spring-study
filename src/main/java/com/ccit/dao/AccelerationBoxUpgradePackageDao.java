package com.ccit.dao;

import com.ccit.entity.AccelerationBoxUpgradePackageEntity;

public interface AccelerationBoxUpgradePackageDao extends BaseDao<AccelerationBoxUpgradePackageEntity, Long> {
    AccelerationBoxUpgradePackageEntity findByFileMd5(String  fileMd5);
}
