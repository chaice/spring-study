package com.ccit.web.controller;

import com.ccit.bean.CustomerInternetCafeBean;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.CustomerInternetCafeService;
import com.ccit.web.interceptor.UserInterceptor;
import com.ccit.vo.CustomerInternetCafeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/internet_cafe/customers")
public class CustomerInternetCafeController extends BaseController {

    @Autowired
    private CustomerInternetCafeService customerInternetCafeService;


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
        List<CustomerInternetCafeVo> customerInternetCafeVos = customerInternetCafeService.searchCustomerInternetCafe(name, portalName, sort);
        return ResponseEntity.ok(customerInternetCafeVos);
    }


    /**
     * 新增客户
     * @param customerInternetCafeBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody CustomerInternetCafeBean customerInternetCafeBean) {
        // 校验客户实体信息
        verifyCustomerInternetCafe(customerInternetCafeBean);
        Validator.notEmpty(customerInternetCafeBean.getZionName(), error(CustomerErrorConstants.CUSTOMER_ZION_NAME_EMPTY));
        customerInternetCafeBean.setCreateUid(UserInterceptor.getLocalUser().getId());

        customerInternetCafeService.saveCustomerInternetCafe(customerInternetCafeBean);
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
        CustomerInternetCafeVo customerInternetCafeVo = customerInternetCafeService.getById(id);
        return ResponseEntity.ok(customerInternetCafeVo);
    }


    /**
     * 修改客户
     * @param id
     * @param customerInternetCafeBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody CustomerInternetCafeBean customerInternetCafeBean) {
        verifyCustomerInternetCafe(customerInternetCafeBean);

        customerInternetCafeBean.setId(id);
        customerInternetCafeBean.setLastUid(UserInterceptor.getLocalUser().getId());
        customerInternetCafeService.updateCustomerInternetCafe(customerInternetCafeBean);

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
        customerInternetCafeService.removeCustomerInternetCafe(id);
        return ResponseEntity.ok(null);
    }

}
