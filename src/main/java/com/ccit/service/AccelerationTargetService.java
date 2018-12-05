package com.ccit.service;

import com.ccit.bean.AccelerationTargetBean;
import com.ccit.vo.AccelerationTargetVo;

import java.util.List;

public interface AccelerationTargetService {

    /**
     * 搜索加速目标
     * @return
     */
    List<AccelerationTargetVo> listAccelerationTarget();

    /**
     * 加速目标详情
     * @param id
     * @return
     */
    AccelerationTargetVo getById(long id);


    /**
     * 更新加速目标
     * @param accelerationTargetList
     */
    void updateAccelerationTargets(List<AccelerationTargetBean> accelerationTargetList);

    /**
     * 更新加速目标
     * @param accelerationTarget
     */
    void updateAccelerationTarget(AccelerationTargetBean accelerationTarget);


    /**
     * 删除加速目标
     * @param id
     */
    void removeAccelerationTarget(long id);
}