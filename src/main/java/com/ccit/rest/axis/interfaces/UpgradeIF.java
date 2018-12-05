package com.ccit.rest.axis.interfaces;

import com.ccit.rest.axis.response.BoxUpgradeRes;
import com.ccit.rest.axis.request.BoxUpgradePackageReq;
import com.ccit.rest.axis.request.BoxUpgradeTaskReq;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("axisUpgradeIF")
public class UpgradeIF extends AbstractAxisIF {

    private final String UPGRADE_TASK_URI = "user/upgrade/tasks";

    private final String UPGRADE_PACKAGE_URI = "user/upgrade/packages";

    public BoxUpgradeRes updateUpgradeTask(List<BoxUpgradeTaskReq> upgradeReqList) {
        return super.patch(UPGRADE_TASK_URI, upgradeReqList, BoxUpgradeRes.class);
    }

    public BoxUpgradeRes removeUpgradeTask(long taskId) {
        return super.delete(UPGRADE_TASK_URI + "/" + taskId, BoxUpgradeRes.class);
    }

    public BoxUpgradeRes updateUpgradePackage(BoxUpgradePackageReq boxUpgradePackageReq) {
        return super.put(UPGRADE_PACKAGE_URI + "/" + boxUpgradePackageReq.getId(), boxUpgradePackageReq, BoxUpgradeRes.class);
    }

    public BoxUpgradeRes removeUpgradePackage(long packageId) {
        return super.delete(UPGRADE_PACKAGE_URI + "/" + packageId, BoxUpgradeRes.class);
    }
}