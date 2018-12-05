package com.ccit.assembler;

import com.ccit.bean.AccelerationDomainEnterpriseBean;
import com.ccit.entity.AccelerationDomainEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AccelerationDomainEnterpriseVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class AccelerationDomainAssembler implements Assembler<AccelerationDomainEnterpriseBean, AccelerationDomainEnterpriseEntity, AccelerationDomainEnterpriseVo> {

    public AccelerationDomainEnterpriseEntity fromBean(AccelerationDomainEnterpriseBean bean) {
        AccelerationDomainEnterpriseEntity entity = new AccelerationDomainEnterpriseEntity();

        entity.setDomain(bean.getDomain());
        entity.setCollectionId(bean.getCollectionId());
        entity.setIpsetId(bean.getIpsetId());
        return entity;
    }


    public AccelerationDomainEnterpriseVo toVo(AccelerationDomainEnterpriseEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccelerationDomainEnterpriseVo vo = new AccelerationDomainEnterpriseVo();

        vo.setId(entity.getId());
        vo.setDomain(entity.getDomain());
        vo.setCreateTime(sdf.format(entity.getCreateTime()));
        vo.setCollectionId(entity.getCollectionId());
        vo.setIpsetId(entity.getIpsetId());
        return vo;
    }


    public List<AccelerationDomainEnterpriseVo> toVos(List<AccelerationDomainEnterpriseEntity> entities) {
        List<AccelerationDomainEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
