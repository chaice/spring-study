package com.ccit.web.controller;

import com.ccit.bean.CustomerEnterpriseBean;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.CustomerEnterpriseService;
import com.ccit.web.interceptor.UserInterceptor;
import com.ccit.vo.CustomerEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/enterprise/customers")
public class CustomerEnterpriseController extends BaseController {

    @Autowired
    private CustomerEnterpriseService customerEnterpriseService;


    /**
     * 搜索客户
     * @param name
     * @param portalName
     * @param sort
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list(String name, String portalName, String sort) {
        List<CustomerEnterpriseVo> customerEnterpriseVos = customerEnterpriseService.searchCustomerEnterprise(name, portalName, sort);
        return ResponseEntity.ok(customerEnterpriseVos);
    }


    /**
     * 新增客户
     * @param customerEnterpriseBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody CustomerEnterpriseBean customerEnterpriseBean) {
        // 校验客户实体信息
        verifyCustomerEnterprise(customerEnterpriseBean);
        Validator.notEmpty(customerEnterpriseBean.getSextantName(), error(CustomerErrorConstants.CUSTOMER_ZION_NAME_EMPTY));
        customerEnterpriseBean.setCreateUid(UserInterceptor.getLocalUser().getId());

        customerEnterpriseService.saveCustomerEnterprise(customerEnterpriseBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 获取客户详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        CustomerEnterpriseVo customerEnterpriseVo = customerEnterpriseService.getById(id);
        return ResponseEntity.ok(customerEnterpriseVo);
    }


    /**
     * 修改客户
     * @param id
     * @param customerEnterpriseBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody CustomerEnterpriseBean customerEnterpriseBean) {
        verifyCustomerEnterprise(customerEnterpriseBean);

        customerEnterpriseBean.setId(id);
        customerEnterpriseBean.setLastUid(UserInterceptor.getLocalUser().getId());
        customerEnterpriseService.updateCustomerEnterprise(customerEnterpriseBean);

        return ResponseEntity.ok(null);
    }


    /**
     * 删除客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        customerEnterpriseService.removeCustomerEnterprise(id);
        return ResponseEntity.ok(null);
    }

}
