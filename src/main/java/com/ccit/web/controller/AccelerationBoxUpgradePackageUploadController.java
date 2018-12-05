package com.ccit.web.controller;

import com.ccit.exception.ParameterException;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.service.AccelerationBoxUpgradePackageService;
import com.ccit.service.NetworkBoxUpgradePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("upgrade/services")
public class AccelerationBoxUpgradePackageUploadController extends BaseController {

    @Autowired
    private AccelerationBoxUpgradePackageService accelerationBoxUpgradePackageService;

    @Autowired
    private NetworkBoxUpgradePackageService netWorkBoxUpgradePackageService;

    @RequestMapping(value = "/acceleration/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> uploadAccelerationUpgradePackage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) {
            String fileName = accelerationBoxUpgradePackageService.uploadUpgradePackage(file);
            Map map = new HashMap();
            map.put("tmpFileName",fileName);
            return ResponseEntity.ok(map);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

    @RequestMapping(value = "/network/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> uploadNetworkUpgradePackage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file != null) {
            String fileName = netWorkBoxUpgradePackageService.uploadUpgradePackage(file);
            Map map = new HashMap();
            map.put("tmpFileName",fileName);
            return ResponseEntity.ok(map);
        }
        throw new ParameterException(CustomerErrorConstants.UPLOAD_FILE_NULL);
    }

}
