package com.ccit.web.controller;

import com.ccit.bean.OrderCheckEnterpriseBean;
import com.ccit.service.OrderEnterpriseService;
import com.ccit.vo.OrderEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping(value = "/enterprise/orders")
public class OrderEnterpriseController extends BaseController {

    @Autowired
    private OrderEnterpriseService orderEnterpriseService;

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> list(HttpServletRequest request, @RequestParam(value = "province",required = false)String province,
                                       @RequestParam(value = "collectionDay",required = false)String collectionDay,
                                       @RequestParam(value = "enterpriseName",required = false)String enterpriseName) {
        List<OrderEnterpriseVo> orders = orderEnterpriseService.listOrder(province,collectionDay,enterpriseName);
        return ResponseEntity.ok(orders);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(orderEnterpriseService.getOrder(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> check(@PathVariable long id, @RequestBody OrderCheckEnterpriseBean orderCheckBean) {
        orderCheckBean.setId(id);
        orderEnterpriseService.checkOrder(orderCheckBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "{id}/modification", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody OrderCheckEnterpriseBean orderCheckBean) {
        orderCheckBean.setId(id);
        orderEnterpriseService.updatePartOrder(orderCheckBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "{id}/available", method = RequestMethod.PUT)
    public ResponseEntity<Object> switchOrder(@PathVariable long id, @RequestBody OrderCheckEnterpriseBean orderCheckBean) {
        orderCheckBean.setId(id);
        orderEnterpriseService.switchOrder(orderCheckBean);
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> export(@RequestParam(value = "province",required = false)String province,
                       @RequestParam(value = "collectionDay",required = false)String collectionDay,
                       @RequestParam(value = "enterpriseName",required = false)String enterpriseName,
                       HttpServletResponse response){
        orderEnterpriseService.exportOrder(response,province,collectionDay,enterpriseName);
        return ResponseEntity.ok(null);
    }

}
