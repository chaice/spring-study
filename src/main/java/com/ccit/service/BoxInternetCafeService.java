package com.ccit.service;


import com.ccit.bean.BoxInternetCafeBean;
import com.ccit.vo.BoxInternetCafeVo;

import java.util.List;


public interface BoxInternetCafeService {


    /**
     * 盒子列表
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    List<BoxInternetCafeVo> listBox(String sn, String customerName, String sort);


    /**
     * 盒子详情
     * @param id
     * @return
     */
    BoxInternetCafeVo getById(long id);


    /**
     * 创建盒子
     * @param boxBean
     * @return
     */
    void saveBox(BoxInternetCafeBean boxBean);


//    /**
//     * 更新盒子
//     * @param boxBean
//     */
//    void updateBox(BoxInternetCafeBean boxBean);


    /**
     * 更新盒子部分字段
     * @param boxBean
     */
    void updateBoxPart(BoxInternetCafeBean boxBean);


    /**
     * 删除盒子
     * @param id
     * @return
     */
    void removeBox(long id);

    void updateBoxPart(List<BoxInternetCafeBean> boxInternetCafeBeanList);
}
