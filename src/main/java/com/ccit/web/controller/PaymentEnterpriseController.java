package com.ccit.web.controller;

import com.ccit.bean.PaymentEnterpriseBean;
import com.ccit.service.PaymentEnterpriseService;
import com.ccit.vo.PaymentEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/enterprise/orders/{orderId}/payments")
public class PaymentEnterpriseController extends BaseController {

    @Autowired
    private PaymentEnterpriseService paymentEnterpriseService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@PathVariable long orderId, @RequestBody PaymentEnterpriseBean paymentEnterpriseBean) {
        verifyPaymentEnterprise(paymentEnterpriseBean);
        paymentEnterpriseService.createPayment(orderId, paymentEnterpriseBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list(@PathVariable long orderId) {
        List<PaymentEnterpriseVo> paymentList = paymentEnterpriseService.listPaymentByOrderId(orderId);
        return ResponseEntity.ok(paymentList);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(paymentEnterpriseService.getPayment(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> modify(@PathVariable long id, @RequestBody PaymentEnterpriseBean paymentEnterpriseBean) {
        verifyPaymentEnterprise(paymentEnterpriseBean);
        paymentEnterpriseService.modifyPayment(id, paymentEnterpriseBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        paymentEnterpriseService.removePayment(id);
        return ResponseEntity.ok(null);
    }

}
