package com.ccit.service;

import com.ccit.bean.NetworkBoxUpgradeTaskBean;
import com.ccit.vo.NetworkBoxUpgradeTaskVo;

import java.util.List;

public interface NetworkBoxUpgradeTaskService {

    List<NetworkBoxUpgradeTaskVo> listUpgradeTask();

    NetworkBoxUpgradeTaskVo getUpgradeTask(long taskId);

    void createUpgradeTask(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean);

    void removeUpgradeTask(long taskId);

    void updateUpgradeTask(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean);

    void updateUpgradeTaskStatus(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean);
}