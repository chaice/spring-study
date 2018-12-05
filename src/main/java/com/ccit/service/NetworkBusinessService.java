package com.ccit.service;

import com.ccit.bean.BoxNetworkBean;
import com.ccit.vo.BoxNetworkVo;

import java.util.List;

public interface NetworkBusinessService {

    /**
     * 根据customerId查询加速业务
     * @return
     */
    List<BoxNetworkVo> listBusiness();


    /**
     * 根据customerId和boxId查询加速业务
     * @param boxId
     * @return
     */
    BoxNetworkVo getBusiness(long boxId);


    /**
     * 更新加速业务
     * @param boxBean
     */
    void updateBusiness(BoxNetworkBean boxBean);

}