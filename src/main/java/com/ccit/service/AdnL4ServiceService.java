package com.ccit.service;

import com.ccit.bean.AdnL4ServiceBean;
import com.ccit.vo.AdnL4ServiceVo;

import java.util.List;

public interface AdnL4ServiceService {

    List<AdnL4ServiceVo> list4LayerServices();

    AdnL4ServiceVo get4LayerService(long serviceId);

    void modify4LayerService(AdnL4ServiceBean adnL4ServiceBean);

    void remove4LayerService(long serviceId);

    void modifyAdnServiceEntry(AdnL4ServiceBean adnL4ServiceBean);
}