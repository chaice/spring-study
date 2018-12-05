package com.ccit.service;


import com.ccit.bean.BoxEnterpriseBean;
import com.ccit.vo.BoxEnterpriseVo;

import java.util.List;


public interface BoxEnterpriseService {


    /**
     * 盒子列表
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    List<BoxEnterpriseVo> listBox(String sn, String customerName, String sort);


    /**
     * 盒子详情
     * @param id
     * @return
     */
    BoxEnterpriseVo getById(long id);


    /**
     * 创建盒子
     * @param boxBean
     * @return
     */
    void saveBox(BoxEnterpriseBean boxBean);


//    /**
//     * 更新盒子
//     * @param boxBean
//     */
//    void updateBox(BoxEnterpriseBean boxBean);


    /**
     * 更新盒子部分字段
     * @param boxBean
     */
    void updateBoxPart(BoxEnterpriseBean boxBean);


    /**
     * 删除盒子
     * @param id
     * @return
     */
    void removeBox(long id);

    void updateBoxPart(List<BoxEnterpriseBean> boxEnterpriseBeanList);
}
