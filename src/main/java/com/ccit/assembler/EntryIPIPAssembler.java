package com.ccit.assembler;

import com.ccit.bean.EntryIPIPBean;
import com.ccit.entity.EntryIPIPEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.EntryIPIPVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class EntryIPIPAssembler implements Assembler<EntryIPIPBean, EntryIPIPEntity, EntryIPIPVo> {

    public EntryIPIPEntity fromBean(EntryIPIPBean bean) {
        EntryIPIPEntity entity = new EntryIPIPEntity();
        entity.setName(bean.getName());
        entity.setMasterIP(bean.getMasterIP());
        entity.setSlaveIP(bean.getSlaveIP());
        return entity;
    }


    public EntryIPIPVo toVo(EntryIPIPEntity entity) {
        EntryIPIPVo vo = new EntryIPIPVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setMasterIP(entity.getMasterIP());
        vo.setSlaveIP(entity.getSlaveIP());
        return vo;
    }


    public List<EntryIPIPVo> toVos(List<EntryIPIPEntity> entities) {
        List<EntryIPIPVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}