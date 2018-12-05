package com.ccit.dao;


import com.ccit.entity.NetworkBoxUpgradeTaskEntity;

public interface NetworkBoxUpgradeTaskDao extends BaseDao<NetworkBoxUpgradeTaskEntity, Long> {

    NetworkBoxUpgradeTaskEntity findByBoxIdPackageId(Long boxId, Long packageId);

    void deleteByPackageId(Long packageId);

    void deleteByBoxId(Long boxId);

    NetworkBoxUpgradeTaskEntity findByPackageIdAndStatus(Long packageId, String status);

}
