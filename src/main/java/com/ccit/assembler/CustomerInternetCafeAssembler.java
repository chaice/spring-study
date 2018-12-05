package com.ccit.assembler;

import com.ccit.bean.CustomerInternetCafeBean;
import com.ccit.entity.CustomerInternetCafeEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.CustomerInternetCafeVo;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class CustomerInternetCafeAssembler implements Assembler<CustomerInternetCafeBean, CustomerInternetCafeEntity, CustomerInternetCafeVo> {


    public CustomerInternetCafeEntity fromBean(CustomerInternetCafeBean bean) {
        CustomerInternetCafeEntity entity = new CustomerInternetCafeEntity();

        entity.setCreateUid(bean.getCreateUid());
        entity.setName(bean.getName());
        entity.setInfo(bean.getInfo());
        entity.setProvince(bean.getProvince());
        entity.setCity(bean.getCity());
        entity.setContact(bean.getContact());
        entity.setContactPhone(bean.getContactPhone());
        entity.setZionName(bean.getZionName());

        return entity;
    }


    public CustomerInternetCafeVo toVo(CustomerInternetCafeEntity entity) {
        CustomerInternetCafeVo vo = new CustomerInternetCafeVo();

        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setInfo(entity.getInfo());
        vo.setContact(entity.getContact());
        vo.setProvince(entity.getProvince());
        vo.setCity(entity.getCity());
        vo.setContact(entity.getContact());
        vo.setContactPhone(entity.getContactPhone());
        vo.setZionName(entity.getZionName());

        return vo;
    }


    public List<CustomerInternetCafeVo> toVos(List<CustomerInternetCafeEntity> entities) {
        List<CustomerInternetCafeVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
