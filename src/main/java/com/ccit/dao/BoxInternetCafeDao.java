package com.ccit.dao;

import com.ccit.entity.BoxInternetCafeEntity;
import com.ccit.enums.BoxStatusEnum;

import java.util.List;

public interface BoxInternetCafeDao extends BaseDao<BoxInternetCafeEntity, Long> {


    /**
     * 盒子列表
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    List<BoxInternetCafeEntity> listBox(String sn, String customerName, String sort);


    /**
     * 根据customerId查询
     * @param customerId
     * @return
     */
    List<BoxInternetCafeEntity> listByCustomerId(long customerId);


    /**
     * 根据customerId统计
     * @param customerId
     * @return
     */
    int countByCustomerId(long customerId);


    /**
     * 根据sn统计
     * @param sn
     * @return
     */
    int countBySn(String sn);


    /**
     * 根据entryId统计
     * @param entryId
     * @return
     */
    int countByEntryId(long entryId);

    void updateAllStatus(BoxStatusEnum offline);

}