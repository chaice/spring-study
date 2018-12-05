package com.ccit.assembler;

import com.ccit.bean.AdnZoneBean;
import com.ccit.entity.AdnZoneEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AdnZoneVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AdnZoneAssembler implements Assembler<AdnZoneBean, AdnZoneEntity, AdnZoneVo> {
    @Override
    public AdnZoneEntity fromBean(AdnZoneBean bean) {
        AdnZoneEntity entity = new AdnZoneEntity();
        entity.setId(bean.getId());
        entity.setZoneName(bean.getZoneName());
        entity.setLatitude(bean.getLatitude());
        entity.setLongitude(bean.getLongitude());
        return entity;
    }

    @Override
    public AdnZoneVo toVo(AdnZoneEntity entity) {
        AdnZoneVo vo = new AdnZoneVo();
        vo.setId(entity.getId());
        vo.setZoneName(entity.getZoneName());
        vo.setLatitude(entity.getLatitude());
        vo.setLongitude(entity.getLongitude());

        return vo;
    }

    @Override
    public List<AdnZoneVo> toVos(List<AdnZoneEntity> entities) {
        List<AdnZoneVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
