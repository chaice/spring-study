package com.ccit.web.controller;

import com.ccit.bean.BoxNetworkBean;
import com.ccit.service.NetworkBusinessService;
import com.ccit.vo.BoxNetworkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/network/business/boxes")
public class NetworkBusinessController extends BaseController {

    @Autowired
    private NetworkBusinessService networkBusinessService;


    /**
     * 盒子加速业务列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<BoxNetworkVo> boxVos = networkBusinessService.listBusiness();
        return ResponseEntity.ok(boxVos);
    }


    /**
     * 盒子加速业务详情
     *
     * @param boxId
     * @return
     */
    @RequestMapping(value = "/{boxId}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long boxId) {
        return ResponseEntity.ok(networkBusinessService.getBusiness(boxId));
    }


    /**
     * 更新盒子加速业务
     *
     * @param boxId
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "/{boxId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long boxId, @RequestBody BoxNetworkBean boxBean) {
        verifyAccelerationNetwork(boxBean);
        boxBean.setId(boxId);
        networkBusinessService.updateBusiness(boxBean);
        return ResponseEntity.ok(null);
    }
}