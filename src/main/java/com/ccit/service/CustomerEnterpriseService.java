package com.ccit.service;


import com.ccit.bean.CustomerEnterpriseBean;
import com.ccit.vo.CustomerEnterpriseVo;

import java.util.List;

public interface CustomerEnterpriseService {


    /**
     * 搜索网吧客户
     * @param name
     * @param portalName
     * @param sort
     * @return
     */
    List<CustomerEnterpriseVo> searchCustomerEnterprise(String name, String portalName, String sort);


    /**
     * 新增网吧客户
     * @param customerEnterpriseBean
     * @return
     */
    void saveCustomerEnterprise(CustomerEnterpriseBean customerEnterpriseBean);


    /**
     * 获取网吧客户详情
     * @param id
     * @return
     */
    CustomerEnterpriseVo getById(long id);


    /**
     * 修改网吧客户
     * @param customerEnterpriseBean
     */
    void updateCustomerEnterprise(CustomerEnterpriseBean customerEnterpriseBean);


    /**
     * 删除网吧客户
     * @param id
     */
    void removeCustomerEnterprise(long id);

}
