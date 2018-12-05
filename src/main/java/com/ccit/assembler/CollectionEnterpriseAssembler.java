package com.ccit.assembler;

import com.ccit.bean.AccelerationCollectionEnterpriseBean;
import com.ccit.entity.AccelerationCollectionEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AccelerationCollectionEnterpriseVo;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class CollectionEnterpriseAssembler implements Assembler<AccelerationCollectionEnterpriseBean, AccelerationCollectionEnterpriseEntity, AccelerationCollectionEnterpriseVo> {
    @Override
    public AccelerationCollectionEnterpriseEntity fromBean(AccelerationCollectionEnterpriseBean bean) {
        AccelerationCollectionEnterpriseEntity collectionEnterpriseEntity = new AccelerationCollectionEnterpriseEntity();

        collectionEnterpriseEntity.setCollectionName(bean.getCollectionName());
        collectionEnterpriseEntity.setCollectionDescription(bean.getCollectionDescription());
        collectionEnterpriseEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        collectionEnterpriseEntity.setType(bean.getType());
        collectionEnterpriseEntity.setAccelerateMode(bean.getAccelerateMode());
        return collectionEnterpriseEntity;
    }

    @Override
    public AccelerationCollectionEnterpriseVo toVo(AccelerationCollectionEnterpriseEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccelerationCollectionEnterpriseVo collectionEnterpriseVo = new AccelerationCollectionEnterpriseVo();

        collectionEnterpriseVo.setId(entity.getId());
        collectionEnterpriseVo.setCollectionName(entity.getCollectionName());
        collectionEnterpriseVo.setCollectionDescription(entity.getCollectionDescription());
        collectionEnterpriseVo.setCreateTime(sdf.format(entity.getCreateTime()));
        collectionEnterpriseVo.setType(entity.getType() != null ? entity.getType() : "");
        collectionEnterpriseVo.setAccelerateMode(entity.getAccelerateMode());
        return collectionEnterpriseVo;
    }

    @Override
    public List<AccelerationCollectionEnterpriseVo> toVos(List<AccelerationCollectionEnterpriseEntity> entities) {
        List<AccelerationCollectionEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
