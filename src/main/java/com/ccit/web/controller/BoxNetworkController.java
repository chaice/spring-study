package com.ccit.web.controller;

import com.ccit.bean.BoxNetworkBean;
import com.ccit.service.BoxNetworkService;
import com.ccit.vo.BoxNetworkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/network/boxes")
public class BoxNetworkController extends BaseController {

    @Autowired
    private BoxNetworkService boxNetworkService;

    /**
     * 盒子列表
     *
     * @param sn
     * @param customerName
     * @param sort
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> list(String sn, String customerName, String sort) {
        List<BoxNetworkVo> boxVoList = boxNetworkService.listBox(sn, customerName, sort);

        return ResponseEntity.ok(boxVoList);
    }

    /**
     * 盒子详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(boxNetworkService.getById(id));
    }

    /**
     * 创建盒子
     *
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody BoxNetworkBean boxBean) {
        verifyBoxNetwork(boxBean);
        boxNetworkService.saveBox(boxBean);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新盒子部分数据
     *
     * @param id
     * @param boxBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> updatePart(@PathVariable long id, @RequestBody BoxNetworkBean boxBean) {
        boxBean.setId(id);
        boxNetworkService.updateBoxPart(boxBean);
        return ResponseEntity.ok(null);
    }

    /**
     * 删除盒子
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> remove(@PathVariable long id) {
        boxNetworkService.removeBox(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新盒子的在线状态
     * @param boxNetworkBeanList
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseEntity<Object> updatePart(@RequestBody List<BoxNetworkBean> boxNetworkBeanList) {
        boxNetworkService.updateBoxPart(boxNetworkBeanList);
        return ResponseEntity.ok(null);
    }
}