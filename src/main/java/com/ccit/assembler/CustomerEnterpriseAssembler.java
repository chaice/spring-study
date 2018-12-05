package com.ccit.assembler;

import com.ccit.bean.CustomerEnterpriseBean;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.CustomerEnterpriseVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CustomerEnterpriseAssembler implements Assembler<CustomerEnterpriseBean, CustomerEnterpriseEntity, CustomerEnterpriseVo> {


    public CustomerEnterpriseEntity fromBean(CustomerEnterpriseBean bean) {
        CustomerEnterpriseEntity entity = new CustomerEnterpriseEntity();

        entity.setCreateUid(bean.getCreateUid());
        entity.setName(bean.getName());
        entity.setInfo(bean.getInfo());
        entity.setProvince(bean.getProvince());
        entity.setCity(bean.getCity());
        entity.setContact(bean.getContact());
        entity.setContactPhone(bean.getContactPhone());
        entity.setSextantName(bean.getSextantName());

        return entity;
    }


    public CustomerEnterpriseVo toVo(CustomerEnterpriseEntity entity) {
        CustomerEnterpriseVo vo = new CustomerEnterpriseVo();

        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setInfo(entity.getInfo());
        vo.setContact(entity.getContact());
        vo.setProvince(entity.getProvince());
        vo.setCity(entity.getCity());
        vo.setContact(entity.getContact());
        vo.setContactPhone(entity.getContactPhone());
        vo.setSextantName(entity.getSextantName());

        return vo;
    }


    public List<CustomerEnterpriseVo> toVos(List<CustomerEnterpriseEntity> entities) {
        List<CustomerEnterpriseVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
