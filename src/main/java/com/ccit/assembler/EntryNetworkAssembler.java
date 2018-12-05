package com.ccit.assembler;

import com.ccit.bean.EntryNetworkBean;
import com.ccit.entity.EntryNetworkEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.EntryNetworkVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class EntryNetworkAssembler implements Assembler<EntryNetworkBean, EntryNetworkEntity, EntryNetworkVo> {

    public EntryNetworkEntity fromBean(EntryNetworkBean bean) {
        EntryNetworkEntity entity = new EntryNetworkEntity();
        entity.setName(bean.getName());
        entity.setMasterIP(bean.getMasterIP());
        entity.setSlaveIP(bean.getSlaveIP());
        entity.setOperators1(bean.getOperators1());
        entity.setOperators2(bean.getOperators2());
        return entity;
    }


    public EntryNetworkVo toVo(EntryNetworkEntity entity) {
        EntryNetworkVo vo = new EntryNetworkVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setMasterIP(entity.getMasterIP());
        vo.setSlaveIP(entity.getSlaveIP());
        vo.setOperators1(entity.getOperators1());
        vo.setOperators2(entity.getOperators2());
        vo.setNeedSync(entity.getNeedSync());
        return vo;
    }


    public List<EntryNetworkVo> toVos(List<EntryNetworkEntity> entities) {
        List<EntryNetworkVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}