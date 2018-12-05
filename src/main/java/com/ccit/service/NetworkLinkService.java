package com.ccit.service;

import com.ccit.bean.NetworkLinkBean;
import com.ccit.vo.NetworkLinkVo;

import java.util.List;

public interface NetworkLinkService {

    List<NetworkLinkVo> listNetworkLink();

    NetworkLinkVo getNetworkLink(long id);

    void modifyPartLink(NetworkLinkBean networkLinkBean);

    void syncLink(NetworkLinkBean networkLinkBean);

    void removeLink(long id);

    void syncLinksStatus(List<NetworkLinkBean> linkBeanList);
}
