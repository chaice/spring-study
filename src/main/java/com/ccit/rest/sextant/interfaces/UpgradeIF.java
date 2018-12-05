package com.ccit.rest.sextant.interfaces;

import com.ccit.rest.sextant.request.BoxUpgradePackageReq;
import com.ccit.rest.sextant.request.BoxUpgradeTaskReq;
import com.ccit.rest.sextant.response.BoxUpgradeRes;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sextantUpgradeIF")
public class UpgradeIF extends AbstractSextantIF {

    private final String UPGRADE_TASK_URI = "upgrade/tasks";

    private final String UPGRADE_PACKAGE_URI = "upgrade/packages";

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