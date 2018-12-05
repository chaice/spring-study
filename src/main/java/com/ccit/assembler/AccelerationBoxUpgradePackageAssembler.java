package com.ccit.assembler;

import com.ccit.bean.AccelerationBoxUpgradePackageBean;
import com.ccit.entity.AccelerationBoxUpgradePackageEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AccelerationBoxUpgradePackageVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
@Component
public class AccelerationBoxUpgradePackageAssembler implements Assembler<AccelerationBoxUpgradePackageBean, AccelerationBoxUpgradePackageEntity, AccelerationBoxUpgradePackageVo> {
    @Override
    public AccelerationBoxUpgradePackageEntity fromBean(AccelerationBoxUpgradePackageBean bean) {
        AccelerationBoxUpgradePackageEntity entity = new AccelerationBoxUpgradePackageEntity();
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
    public AccelerationBoxUpgradePackageVo toVo(AccelerationBoxUpgradePackageEntity entity) {
        AccelerationBoxUpgradePackageVo vo = new AccelerationBoxUpgradePackageVo();
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
    public List<AccelerationBoxUpgradePackageVo> toVos(List<AccelerationBoxUpgradePackageEntity> entities) {
        List<AccelerationBoxUpgradePackageVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
