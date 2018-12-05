package com.ccit.service;

import com.ccit.bean.AccelerationBoxUpgradeTaskBean;
import com.ccit.vo.AccelerationBoxUpgradeTaskVo;

import java.util.List;

public interface AccelerationBoxUpgradeTaskService {

    List<AccelerationBoxUpgradeTaskVo> listUpgradeTask();

    AccelerationBoxUpgradeTaskVo getUpgradeTask(long taskId);

    void createUpgradeTask(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean);

    void removeUpgradeTask(long taskId);

    void updateUpgradeTask(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean);

    void updateUpgradeTaskStatus(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean);
}