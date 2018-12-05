package com.ccit.assembler;

import com.ccit.bean.EntryEnterpriseSSBean;
import com.ccit.entity.EntryEnterpriseSSEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.EntryEnterpriseSSVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class EntryEnterpriseSSAssembler implements Assembler<EntryEnterpriseSSBean, EntryEnterpriseSSEntity, EntryEnterpriseSSVo> {

    public EntryEnterpriseSSEntity fromBean(EntryEnterpriseSSBean bean) {
        EntryEnterpriseSSEntity entity = new EntryEnterpriseSSEntity();

        entity.setName(bean.getName());
        entity.setIp(bean.getIp());
        entity.setPort(bean.getPort());
        entity.setDns(bean.getDns());
        entity.setPassword(bean.getPassword());

        return entity;
    }


    public EntryEnterpriseSSVo toVo(EntryEnterpriseSSEntity entity) {
        EntryEnterpriseSSVo vo = new EntryEnterpriseSSVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());

        vo.setIp(entity.getIp());
        vo.setPort(entity.getPort());
        vo.setDns(entity.getDns());
        vo.setPassword(entity.getPassword());

        return vo;
    }


    public List<EntryEnterpriseSSVo> toVos(List<EntryEnterpriseSSEntity> entities) {
        List<EntryEnterpriseSSVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }

}