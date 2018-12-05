package com.ccit.assembler;

import com.ccit.bean.NetworkBoxUpgradePackageBean;
import com.ccit.entity.NetworkBoxUpgradePackageEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.NetworkBoxUpgradePackageVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class NetworkBoxUpgradePackageAssembler implements Assembler<NetworkBoxUpgradePackageBean, NetworkBoxUpgradePackageEntity, NetworkBoxUpgradePackageVo> {
    @Override
    public NetworkBoxUpgradePackageEntity fromBean(NetworkBoxUpgradePackageBean bean) {
        NetworkBoxUpgradePackageEntity entity = new NetworkBoxUpgradePackageEntity();
        entity.setCreateTime(bean.getCreateTime());
        entity.setPackageVersion(bean.getPackageVersion());
        entity.setSuitableVersion(bean.getSuitableVersion());
        entity.setFileMd5(bean.getFileMd5());
        entity.setFileName(bean.getFileName());
        entity.setDownloadPassword(bean.getDownloadPassword());
        entity.setDownloadUserName(bean.getDownloadUserName());
        entity.setDownloadUrl(bean.getDownloadUrl());
        return entity;
    }

    @Override
    public NetworkBoxUpgradePackageVo toVo(NetworkBoxUpgradePackageEntity entity) {
        NetworkBoxUpgradePackageVo vo = new NetworkBoxUpgradePackageVo();
        vo.setId(entity.getId());
        vo.setPackageVersion(entity.getPackageVersion());
        vo.setSuitableVersion(entity.getSuitableVersion());
        vo.setFileName(entity.getFileName());
        vo.setCreateTime(entity.getCreateTime());
        vo.setFileMd5(entity.getFileMd5());
        vo.setDownloadUserName(entity.getDownloadUserName());
        vo.setDownloadPassword(entity.getDownloadPassword());
        vo.setDownloadUrl(entity.getDownloadUrl());
        return vo;
    }

    @Override
    public List<NetworkBoxUpgradePackageVo> toVos(List<NetworkBoxUpgradePackageEntity> entities) {
        List<NetworkBoxUpgradePackageVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
