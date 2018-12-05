package com.ccit.service;

import com.ccit.bean.AdnL3ServiceBean;
import com.ccit.vo.AdnL3ServiceVo;

import java.util.List;

public interface AdnL3ServiceService {

    List<AdnL3ServiceVo> list3LayerServices();

    AdnL3ServiceVo get3LayerService(long serviceId);

    void modify3LayerService(AdnL3ServiceBean adnL3ServiceBean);

    void remove3LayerService(long serviceId);

    void modifyAdnServiceEntry(AdnL3ServiceBean adnL3ServiceBean);
}