package com.ccit.service;

import com.ccit.bean.AccelerationBoxUpgradePackageBean;
import com.ccit.vo.AccelerationBoxUpgradePackageVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AccelerationBoxUpgradePackageService {
    List<AccelerationBoxUpgradePackageVo> listUpgradePackage();

    void createUpgradePackage(AccelerationBoxUpgradePackageBean upgradePackageBean) throws IOException;

    void removePackage(long packageId);

    AccelerationBoxUpgradePackageVo findById(long packageId);

    void modifyUpgradePackage(AccelerationBoxUpgradePackageBean upgradePackageBean) throws IOException;

    String uploadUpgradePackage(MultipartFile file);
}
