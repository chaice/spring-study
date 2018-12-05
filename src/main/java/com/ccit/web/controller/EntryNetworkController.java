package com.ccit.web.controller;

import com.ccit.bean.EntryNetworkBean;
import com.ccit.service.EntryNetworkService;
import com.ccit.vo.EntryNetworkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/network/entries")
public class EntryNetworkController extends BaseController {

    @Autowired
    private EntryNetworkService entryNetworkService;

    /**
     * 加速入口列表
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Object> list() {
        List<EntryNetworkVo> entryVos = entryNetworkService.searchEntry();
        return ResponseEntity.ok(entryVos);
    }

    /**
     * 加速入口详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> get(@PathVariable long id) {
        return ResponseEntity.ok(entryNetworkService.getById(id));
    }

    /**
     * 新增加速入口
     *
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody EntryNetworkBean entryBean) {
        verifyEntryNetwork(entryBean);
        entryNetworkService.createEntry(entryBean);
        return ResponseEntity.ok(null);
    }

    /**
     * 更新加速入口
     *
     * @param id
     * @param entryBean
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@PathVariable long id, @RequestBody EntryNetworkBean entryBean) {
        entryBean.setId(id);
        if (entryBean.getNeedSync() != null) {
            entryNetworkService.syncEntry(entryBean);
        } else {
            verifyEntryNetwork(entryBean);
            entryNetworkService.updateEntry(entryBean);
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 删除加速入口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> remove(@PathVariable long id) {
        entryNetworkService.removeEntry(id);
        return ResponseEntity.ok(null);
    }

}