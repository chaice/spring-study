package com.ccit.assembler;

import com.ccit.bean.AdnCustomerDomainBean;
import com.ccit.entity.AdnCustomerDomainEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.AdnCustomerDomainVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AdnCustomerDomainAssembler implements Assembler<AdnCustomerDomainBean, AdnCustomerDomainEntity, AdnCustomerDomainVo> {
    @Override
    public AdnCustomerDomainEntity fromBean(AdnCustomerDomainBean bean) {
        AdnCustomerDomainEntity entity = new AdnCustomerDomainEntity();
        entity.setId(bean.getId());
        entity.setCustomerId(bean.getCustomerId());
        entity.setSecondLevelDomain(bean.getSecondLevelDomain());
        return entity;
    }

    @Override
    public AdnCustomerDomainVo toVo(AdnCustomerDomainEntity entity) {
        AdnCustomerDomainVo vo = new AdnCustomerDomainVo();
        vo.setId(entity.getId());
        vo.setCustomerId(entity.getCustomerId());
        vo.setSecondLevelDomain(entity.getSecondLevelDomain());
        return vo;
    }

    @Override
    public List<AdnCustomerDomainVo> toVos(List<AdnCustomerDomainEntity> entities) {
        List<AdnCustomerDomainVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
