package com.ccit.service;

import com.ccit.bean.EntryNetworkBean;
import com.ccit.vo.EntryNetworkVo;

import java.util.List;

public interface EntryNetworkService {

    /**
     * 搜索加速入口
     *
     * @return
     */
    List<EntryNetworkVo> searchEntry();


    /**
     * 新增加速入口
     *
     * @param entryBean
     */
    void createEntry(EntryNetworkBean entryBean);


    /**
     * 加速入口详情
     *
     * @param id
     * @return
     */
    EntryNetworkVo getById(long id);


    /**
     * 更新加速入口
     *
     * @param entryBean
     */
    void updateEntry(EntryNetworkBean entryBean);


    /**
     * 删除加速入口
     *
     * @param id
     */
    void removeEntry(long id);

    void syncEntry(EntryNetworkBean entryBean);
}