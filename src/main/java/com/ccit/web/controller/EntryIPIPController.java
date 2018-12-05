package com.ccit.web.controller;

import com.ccit.bean.EntryIPIPBean;
import com.ccit.service.EntryIPIPService;
import com.ccit.vo.EntryIPIPVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/IPIP/entries")
public class EntryIPIPController extends BaseController {

    @Autowired
    private EntryIPIPService businessEntryService;


    /**
     * 加速入口列表
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<EntryIPIPVo> entryVos = businessEntryService.searchEntry();
        return ResponseEntity.ok(entryVos);
    }


    /**
     * 新增加速入口
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody EntryIPIPBean entryBean) {
        verifyEntryIPIP(entryBean);
        businessEntryService.createEntry(entryBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 加速入口详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(businessEntryService.getById(id));
    }


    /**
     * 更新加速入口
     * @param id
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody EntryIPIPBean entryBean) {
        verifyEntryIPIP(entryBean);
        entryBean.setId(id);
        businessEntryService.updateEntry(entryBean);
        return ResponseEntity.ok(null);
    }


    /**
     * 删除加速入口
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable long id) {
        businessEntryService.removeEntry(id);
        return ResponseEntity.ok(null);
    }

}