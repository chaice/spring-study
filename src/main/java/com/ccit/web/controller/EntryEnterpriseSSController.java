package com.ccit.web.controller;

import com.ccit.bean.EntryEnterpriseSSBean;
import com.ccit.service.EntryEnterpriseService;
import com.ccit.vo.EntryEnterpriseSSVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/enterprise/ss/entries")
public class EntryEnterpriseSSController extends BaseController {

    @Autowired
    private EntryEnterpriseService businessEntryService;


    /**
     * 加速入口列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<EntryEnterpriseSSVo> entryVos = businessEntryService.searchSSEntry();
        return ResponseEntity.ok(entryVos);
    }


    /**
     * 新增加速入口
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody EntryEnterpriseSSBean entryBean) {
        verifyEntryEnterpriseSS(entryBean);
        businessEntryService.createSSEntry(entryBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 加速入口详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(businessEntryService.getSSEntryById(id));
    }


    /**
     * 更新加速入口
     * @param id
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody EntryEnterpriseSSBean entryBean) {
        verifyEntryEnterpriseSS(entryBean);
        entryBean.setId(id);
        businessEntryService.updateSSEntry(entryBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 删除加速入口
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable long id) {
        businessEntryService.removeSSEntry(id);
        return ResponseEntity.ok(null);
    }

}