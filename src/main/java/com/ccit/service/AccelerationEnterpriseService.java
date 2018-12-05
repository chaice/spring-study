package com.ccit.service;

import com.ccit.bean.BoxEnterpriseBean;
import com.ccit.vo.BoxEnterpriseVo;

import java.util.List;

public interface AccelerationEnterpriseService {

    /**
     * 根据customerId查询加速业务
     * @return
     */
    List<BoxEnterpriseVo> listAcceleration();


    /**
     * 根据customerId和boxId查询加速业务
     * @param boxId
     * @return
     */
    BoxEnterpriseVo getAcceleration(long boxId);


    /**
     * 更新加速业务
     * @param boxBean
     */
    void updateAcceleration(BoxEnterpriseBean boxBean);

}