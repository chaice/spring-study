package com.ccit.web.controller;

import com.ccit.bean.AccelerationBoxUpgradeTaskBean;
import com.ccit.service.AccelerationBoxUpgradeTaskService;
import com.ccit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/acceleration/upgrade/tasks")
public class AccelerationBoxUpgradeTaskController extends BaseController {

    @Autowired
    private AccelerationBoxUpgradeTaskService accelerationBoxUpgradeTaskService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listUpgradeTask() {
        return ResponseEntity.ok(accelerationBoxUpgradeTaskService.listUpgradeTask());
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUpgradeTask(@PathVariable long taskId) {
        return ResponseEntity.ok(accelerationBoxUpgradeTaskService.getUpgradeTask(taskId));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createUpgradeTask(@RequestBody AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean) {
        verifyAccelerationBoxUpgradeTaskBean(accelerationBoxUpgradeTaskBean);
        accelerationBoxUpgradeTaskService.createUpgradeTask(accelerationBoxUpgradeTaskBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateUpgradeTask(@PathVariable long taskId, @RequestBody AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean) {
        accelerationBoxUpgradeTaskBean.setId(taskId);
        if (StringUtils.isNull(accelerationBoxUpgradeTaskBean.getStatus())) {
            verifyAccelerationBoxUpgradeTaskBean(accelerationBoxUpgradeTaskBean);
            accelerationBoxUpgradeTaskService.updateUpgradeTask(accelerationBoxUpgradeTaskBean);
        } else {
            accelerationBoxUpgradeTaskService.updateUpgradeTaskStatus(accelerationBoxUpgradeTaskBean);
        }
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{taskId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> removeUpgradeTask(@PathVariable long taskId) {
        accelerationBoxUpgradeTaskService.removeUpgradeTask(taskId);
        return ResponseEntity.ok(null);
    }
}
