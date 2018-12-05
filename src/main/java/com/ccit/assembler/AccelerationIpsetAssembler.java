package com.ccit.assembler;

import com.ccit.bean.AccelerationIpsetEnterpriseBean;
import com.ccit.entity.AccelerationIpsetEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AccelerationIpsetEnterpriseVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class AccelerationIpsetAssembler implements Assembler<AccelerationIpsetEnterpriseBean, AccelerationIpsetEnterpriseEntity, AccelerationIpsetEnterpriseVo> {

    public AccelerationIpsetEnterpriseEntity fromBean(AccelerationIpsetEnterpriseBean bean) {
        AccelerationIpsetEnterpriseEntity entity = new AccelerationIpsetEnterpriseEntity();

        entity.setIpsetName(bean.getIpsetName());
        entity.setPortTotal(bean.getPortTotal());
        entity.setTransportProtocol(bean.getTransportProtocol());
        entity.setCollectionId(bean.getCollectionId());
        entity.setAccelerateMode(bean.getAccelerateMode());

        return entity;
    }


    public AccelerationIpsetEnterpriseVo toVo(AccelerationIpsetEnterpriseEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccelerationIpsetEnterpriseVo vo = new AccelerationIpsetEnterpriseVo();

        vo.setId(entity.getId());
        vo.setIpsetName(entity.getIpsetName());
        vo.setCreateTime(sdf.format(entity.getCreateTime()));
        vo.setCollectionId(entity.getCollectionId());
        vo.setTransportProtocol(entity.getTransportProtocol());
        vo.setPortTotal(entity.getPortTotal());
        vo.setSequence(entity.getSequence());
        vo.setEnable(entity.getEnable());
        vo.setAccelerateMode(entity.getAccelerateMode());

        return vo;
    }


    public List<AccelerationIpsetEnterpriseVo> toVos(List<AccelerationIpsetEnterpriseEntity> entities) {
        List<AccelerationIpsetEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
