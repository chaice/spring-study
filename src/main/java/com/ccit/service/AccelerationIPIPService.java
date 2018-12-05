package com.ccit.service;

import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.vo.BoxInternetCafeVo;

import java.util.List;

public interface AccelerationIPIPService {

    /**
     * 根据customerId查询加速业务
     * @return
     */
    List<BoxInternetCafeVo> listAcceleration();


    /**
     * 根据customerId和boxId查询加速业务
     * @param boxId
     * @return
     */
    BoxInternetCafeVo getAcceleration(long boxId);


    /**
     * 更新加速业务
     * @param boxBean
     */
    void updateAcceleration(BoxInternetCafeBean boxBean);

}