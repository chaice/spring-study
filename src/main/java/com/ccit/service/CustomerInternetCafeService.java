package com.ccit.service;


import com.ccit.bean.CustomerInternetCafeBean;
import com.ccit.vo.CustomerInternetCafeVo;

import java.util.List;

public interface CustomerInternetCafeService {


    /**
     * 搜索网吧客户
     * @param name
     * @param portalName
     * @param sort
     * @return
     */
    List<CustomerInternetCafeVo> searchCustomerInternetCafe(String name, String portalName, String sort);


    /**
     * 新增网吧客户
     * @param customerInternetCafeBean
     * @return
     */
    void saveCustomerInternetCafe(CustomerInternetCafeBean customerInternetCafeBean);


    /**
     * 获取网吧客户详情
     * @param id
     * @return
     */
    CustomerInternetCafeVo getById(long id);


    /**
     * 修改网吧客户
     * @param customerInternetCafeBean
     */
    void updateCustomerInternetCafe(CustomerInternetCafeBean customerInternetCafeBean);


    /**
     * 删除网吧客户
     * @param id
     */
    void removeCustomerInternetCafe(long id);

}
