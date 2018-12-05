package com.ccit.assembler;

import com.ccit.bean.AccelerationCidrEnterpriseBean;
import com.ccit.entity.AccelerationCidrEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AccelerationCidrEnterpriseVo;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class AccelerationCidrAssembler implements Assembler<AccelerationCidrEnterpriseBean, AccelerationCidrEnterpriseEntity, AccelerationCidrEnterpriseVo> {

    public AccelerationCidrEnterpriseEntity fromBean(AccelerationCidrEnterpriseBean bean) {
        AccelerationCidrEnterpriseEntity entity = new AccelerationCidrEnterpriseEntity();

        entity.setCidr(bean.getCidr());
        entity.setIpsetId(bean.getIpsetId());
        entity.setCollectionId(bean.getCollectionId());

        return entity;
    }


    public AccelerationCidrEnterpriseVo toVo(AccelerationCidrEnterpriseEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccelerationCidrEnterpriseVo vo = new AccelerationCidrEnterpriseVo();

        vo.setId(entity.getId());
        vo.setCidr(entity.getCidr());
        vo.setCreateTime(sdf.format(entity.getCreateTime()));
        vo.setCollectionId(entity.getCollectionId());
        vo.setIpsetId(entity.getIpsetId());
        vo.setCountry(entity.getCountry());

        return vo;
    }


    public List<AccelerationCidrEnterpriseVo> toVos(List<AccelerationCidrEnterpriseEntity> entities) {
        List<AccelerationCidrEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
