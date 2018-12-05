package com.ccit.service;

import com.ccit.bean.EntryIPIPBean;
import com.ccit.vo.EntryIPIPVo;

import java.util.List;

public interface EntryIPIPService {

    /**
     * 搜索加速入口
     * @return
     */
    List<EntryIPIPVo> searchEntry();


    /**
     * 新增加速入口
     * @param entryBean
     */
    void createEntry(EntryIPIPBean entryBean);


    /**
     * 加速入口详情
     * @param id
     * @return
     */
    EntryIPIPVo getById(long id);


    /**
     * 更新加速入口
     * @param entryBean
     */
    void updateEntry(EntryIPIPBean entryBean);


    /**
     * 删除加速入口
     * @param id
     */
    void removeEntry(long id);
}