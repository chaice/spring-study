package com.ccit.service;

import com.ccit.bean.NetworkBoxUpgradePackageBean;
import com.ccit.vo.NetworkBoxUpgradePackageVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface NetworkBoxUpgradePackageService {
    List<NetworkBoxUpgradePackageVo> listUpgradePackage();

    void createUpgradePackage(NetworkBoxUpgradePackageBean upgradePackageBean) throws IOException;

    void removePackage(long packageId);

    NetworkBoxUpgradePackageVo findById(long packageId);

    void modifyUpgradePackage(NetworkBoxUpgradePackageBean upgradePackageBean) throws IOException;

    String uploadUpgradePackage(MultipartFile file);
}
