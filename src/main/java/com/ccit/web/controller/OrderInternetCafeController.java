package com.ccit.web.controller;

import com.ccit.bean.OrderCheckInternetCafeBean;
import com.ccit.service.OrderInternetCafeService;
import com.ccit.vo.OrderInternetCafeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = "/internet_cafe/orders")
public class OrderInternetCafeController extends BaseController {

    @Autowired
    private OrderInternetCafeService orderInternetCafeService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list() {
        List<OrderInternetCafeVo> orders = orderInternetCafeService.listOrder();
        return ResponseEntity.ok(orders);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(orderInternetCafeService.getOrder(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> check(@PathVariable long id, @RequestBody OrderCheckInternetCafeBean orderCheckBean) {
        orderCheckBean.setId(id);
        orderInternetCafeService.checkOrder(orderCheckBean);
        return ResponseEntity.ok(null);
    }

}
