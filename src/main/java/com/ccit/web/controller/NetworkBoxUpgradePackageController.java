package com.ccit.web.controller;

import com.ccit.bean.NetworkBoxUpgradePackageBean;
import com.ccit.exception.ParameterException;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.service.NetworkBoxUpgradePackageService;
import com.ccit.util.StringUtils;
import com.ccit.vo.NetworkBoxUpgradePackageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/network/upgrade/packages")
public class NetworkBoxUpgradePackageController extends BaseController {

    @Autowired
    private NetworkBoxUpgradePackageService networkBoxUpgradePackageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listUpgradePackage() {
        List<NetworkBoxUpgradePackageVo> upgradePackageVos = networkBoxUpgradePackageService.listUpgradePackage();
        return ResponseEntity.ok(upgradePackageVos);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getUpgradePackage(@PathVariable long packageId) {
        NetworkBoxUpgradePackageVo upgradePackageVo = networkBoxUpgradePackageService.findById(packageId);
        return ResponseEntity.ok(upgradePackageVo);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> deleteUpgradePackage(@PathVariable long packageId) {
        networkBoxUpgradePackageService.removePackage(packageId);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> createUpgradePackage(@RequestBody NetworkBoxUpgradePackageBean upgradePackageBean) throws IOException, InterruptedException {
        verifyNetworkBoxUpgradePackageBean(upgradePackageBean);
        if (!StringUtils.isNull(upgradePackageBean.getTmpFileName())) {
            networkBoxUpgradePackageService.createUpgradePackage(upgradePackageBean);

            return ResponseEntity.ok(null);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

    @RequestMapping(value = "/{packageId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> updateUpgradePackage(@PathVariable long packageId, @RequestBody NetworkBoxUpgradePackageBean upgradePackageBean ) throws IOException {
        verifyNetworkBoxUpgradePackSuitBean(upgradePackageBean);
        upgradePackageBean.setId(packageId);
        networkBoxUpgradePackageService.modifyUpgradePackage(upgradePackageBean);
        return ResponseEntity.ok(null);
    }

}
