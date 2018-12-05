package com.ccit.assembler;

import com.ccit.bean.AccelerationTargetBean;
import com.ccit.entity.AccelerationTargetEntity;
import com.ccit.model.Assembler;
import com.ccit.util.StringUtils;
import com.ccit.vo.AccelerationTargetVo;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

@Component
public class AccelerationTargetAssembler implements Assembler<AccelerationTargetBean, AccelerationTargetEntity, AccelerationTargetVo> {


    public AccelerationTargetEntity fromBean(AccelerationTargetBean bean) {
        AccelerationTargetEntity entity = new AccelerationTargetEntity();

        entity.setCidr(bean.getCidr());
        if (StringUtils.isBlank(bean.getCategory())) {
            entity.setCategory("unknown");
        } else {
            entity.setCategory(bean.getCategory());
        }
        if (StringUtils.isBlank(bean.getSubcategory())) {
            entity.setSubcategory("unknown");
        } else {
            entity.setSubcategory(bean.getSubcategory());
        }
        entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));

        return entity;
    }


    public AccelerationTargetVo toVo(AccelerationTargetEntity entity) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AccelerationTargetVo vo = new AccelerationTargetVo();

        vo.setId(entity.getId());
        vo.setCidr(entity.getCidr());
        vo.setUpdateTime(sdf.format(entity.getUpdateTime()));
        vo.setCategory(entity.getCategory());
        vo.setSubcategory(entity.getSubcategory());

        return vo;
    }


    public List<AccelerationTargetVo> toVos(List<AccelerationTargetEntity> entities) {
        List<AccelerationTargetVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
