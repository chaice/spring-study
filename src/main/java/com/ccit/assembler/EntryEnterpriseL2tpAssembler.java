package com.ccit.assembler;

import com.ccit.bean.EntryEnterpriseL2TPBean;
import com.ccit.entity.EntryEnterpriseL2tpEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.EntryEnterpriseL2TPVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class EntryEnterpriseL2tpAssembler implements Assembler<EntryEnterpriseL2TPBean, EntryEnterpriseL2tpEntity, EntryEnterpriseL2TPVo> {

    public EntryEnterpriseL2tpEntity fromBean(EntryEnterpriseL2TPBean bean) {
        EntryEnterpriseL2tpEntity entity = new EntryEnterpriseL2tpEntity();
        entity.setName(bean.getName());
        entity.setMasterIP(bean.getMasterIP());
        entity.setSlaveIP(bean.getSlaveIP());
        return entity;
    }


    public EntryEnterpriseL2TPVo toVo(EntryEnterpriseL2tpEntity entity) {
        EntryEnterpriseL2TPVo vo = new EntryEnterpriseL2TPVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setMasterIP(entity.getMasterIP());
        vo.setSlaveIP(entity.getSlaveIP());
        vo.setNeedSync(entity.getNeedSync());
        return vo;
    }


    public List<EntryEnterpriseL2TPVo> toVos(List<EntryEnterpriseL2tpEntity> entities) {
        List<EntryEnterpriseL2TPVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}