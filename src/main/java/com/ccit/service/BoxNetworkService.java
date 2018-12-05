package com.ccit.service;

import com.ccit.bean.BoxNetworkBean;
import com.ccit.vo.BoxNetworkVo;

import java.util.List;

public interface BoxNetworkService {


    /**
     * 盒子列表
     *
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    List<BoxNetworkVo> listBox(String sn, String customerName, String sort);


    /**
     * 盒子详情
     *
     * @param id
     * @return
     */
    BoxNetworkVo getById(long id);


    /**
     * 创建盒子
     *
     * @param boxBean
     * @return
     */
    void saveBox(BoxNetworkBean boxBean);


    /**
     * 更新盒子部分字段
     *
     * @param boxBean
     */
    void updateBoxPart(BoxNetworkBean boxBean);


    /**
     * 删除盒子
     *
     * @param id
     * @return
     */
    void removeBox(long id);

    void updateBoxPart(List<BoxNetworkBean> boxNetworkBeanList);
}
