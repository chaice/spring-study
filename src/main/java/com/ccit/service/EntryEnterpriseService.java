package com.ccit.service;

import com.ccit.bean.EntryEnterpriseSSGroupBean;
import com.ccit.bean.EntryEnterpriseSSBean;
import com.ccit.bean.EntryEnterpriseL2TPBean;
import com.ccit.vo.EntryEnterpriseSSGroupVo;
import com.ccit.vo.EntryEnterpriseSSVo;
import com.ccit.vo.EntryEnterpriseL2TPVo;

import java.util.List;

public interface EntryEnterpriseService {

    /**
     * 获取加速入口组列表
     *
     * @return
     */
    List<EntryEnterpriseSSGroupVo> listSSEntryGroup();

    /**
     * 获取加速入口组
     *
     * @param groupId
     * @return
     */
    EntryEnterpriseSSGroupVo getSSEntryGroup(long groupId);

    /**
     * 创建加速入口组
     *
     * @param groupSSBean
     */
    void createSSEntryGroup(EntryEnterpriseSSGroupBean groupSSBean);

    /**
     * 修改加速入口组
     * @param groupSSBean
     */
    void updateSSEntryGroup(EntryEnterpriseSSGroupBean groupSSBean);

    /**
     * 删除加速入口组
     *
     * @param groupId
     */
    void removeSSEntryGroup(long groupId);

    /**
     * 搜索加速入口
     *
     * @return
     */
    List<EntryEnterpriseSSVo> searchSSEntry();


    /**
     * 新增加速入口
     *
     * @param entryBean
     */
    void createSSEntry(EntryEnterpriseSSBean entryBean);


    /**
     * 加速入口详情
     *
     * @param id
     * @return
     */
    EntryEnterpriseSSVo getSSEntryById(long id);


    /**
     * 更新加速入口
     *
     * @param entryBean
     */
    void updateSSEntry(EntryEnterpriseSSBean entryBean);


    /**
     * 删除加速入口
     *
     * @param id
     */
    void removeSSEntry(long id);

    List<EntryEnterpriseL2TPVo> searchL2tpEntry();

    EntryEnterpriseL2TPVo getL2tpById(long id);

    void removeL2tpEntry(long id);

    void updateL2tpEntry(EntryEnterpriseL2TPBean entryBean);

    void createL2tpEntry(EntryEnterpriseL2TPBean entryBean);

    void syncL2tpEntry(EntryEnterpriseL2TPBean entryEnterpriseL2TPBean);

    void syncSSEntryGroup(EntryEnterpriseSSGroupBean entryEnterpriseSSGroupBean);
}