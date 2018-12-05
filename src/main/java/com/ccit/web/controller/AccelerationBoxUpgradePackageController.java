package com.ccit.web.controller;

import com.ccit.bean.AccelerationBoxUpgradePackageBean;
import com.ccit.exception.ParameterException;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.service.AccelerationBoxUpgradePackageService;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationBoxUpgradePackageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/acceleration/upgrade/packages")
public class AccelerationBoxUpgradePackageController extends BaseController {

    @Autowired
    private AccelerationBoxUpgradePackageService accelerationBoxUpgradePackageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listUpgradePackage() {
        List<AccelerationBoxUpgradePackageVo> upgradePackageVos = accelerationBoxUpgradePackageService.listUpgradePackage();
        return ResponseEntity.ok(upgradePackageVos);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUpgradePackage(@PathVariable long packageId) {
        AccelerationBoxUpgradePackageVo upgradePackageVo = accelerationBoxUpgradePackageService.findById(packageId);
        return ResponseEntity.ok(upgradePackageVo);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> deleteUpgradePackage(@PathVariable long packageId) {
        accelerationBoxUpgradePackageService.removePackage(packageId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createUpgradePackage(@RequestBody AccelerationBoxUpgradePackageBean upgradePackageBean) throws IOException, InterruptedException {
        verifyAccelerationBoxUpgradePackageBean(upgradePackageBean);
        if (!StringUtils.isNull(upgradePackageBean.getTmpFileName())) {
            accelerationBoxUpgradePackageService.createUpgradePackage(upgradePackageBean);

            return ResponseEntity.ok(null);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateUpgradePackage(@PathVariable long packageId, @RequestBody AccelerationBoxUpgradePackageBean upgradePackageBean ) throws IOException {
        verifyAccelerationBoxUpgradePackSuitBean(upgradePackageBean);
        upgradePackageBean.setId(packageId);
        accelerationBoxUpgradePackageService.modifyUpgradePackage(upgradePackageBean);
        return ResponseEntity.ok(null);
    }

}
