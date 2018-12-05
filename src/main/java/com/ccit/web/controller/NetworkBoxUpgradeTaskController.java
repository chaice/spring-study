package com.ccit.web.controller;

import com.ccit.bean.NetworkBoxUpgradeTaskBean;
import com.ccit.service.NetworkBoxUpgradeTaskService;
import com.ccit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/network/upgrade/tasks")
public class NetworkBoxUpgradeTaskController extends BaseController {

    @Autowired
    private NetworkBoxUpgradeTaskService networkBoxUpgradeTaskService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listUpgradeTask() {
        return ResponseEntity.ok(networkBoxUpgradeTaskService.listUpgradeTask());
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUpgradeTask(@PathVariable long taskId) {
        return ResponseEntity.ok(networkBoxUpgradeTaskService.getUpgradeTask(taskId));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createUpgradeTask(@RequestBody NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean) {
        verifyNetworkBoxUpgradeTaskBean(networkBoxUpgradeTaskBean);
        networkBoxUpgradeTaskService.createUpgradeTask(networkBoxUpgradeTaskBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateUpgradeTask(@PathVariable long taskId, @RequestBody NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean) {
        networkBoxUpgradeTaskBean.setId(taskId);
        if (StringUtils.isNull(networkBoxUpgradeTaskBean.getStatus())) {
            verifyNetworkBoxUpgradeTaskBean(networkBoxUpgradeTaskBean);
            networkBoxUpgradeTaskService.updateUpgradeTask(networkBoxUpgradeTaskBean);
        } else {
            networkBoxUpgradeTaskService.updateUpgradeTaskStatus(networkBoxUpgradeTaskBean);
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeUpgradeTask(@PathVariable long taskId) {
        networkBoxUpgradeTaskService.removeUpgradeTask(taskId);
        return ResponseEntity.ok(null);
    }
}
