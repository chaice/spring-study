package com.ccit.dao;

import com.ccit.entity.AccelerationBoxUpgradeTaskEntity;

public interface AccelerationBoxUpgradeTaskDao extends BaseDao<AccelerationBoxUpgradeTaskEntity, Long> {

    AccelerationBoxUpgradeTaskEntity findByBoxIdPackageId(Long boxId, Long packageId);

    void deleteByPackageId(Long packageId);

    void deleteByBoxId(Long boxId);

    AccelerationBoxUpgradeTaskEntity findByPackageIdAndStatus(Long packageId, String status);
}
