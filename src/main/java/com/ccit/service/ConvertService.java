package com.ccit.service;

import com.ccit.bean.PerformanceQueryBean;
import com.ccit.entity.PerformanceAdnEntryTrafficEntity;
import com.ccit.vo.PerformanceTrafficVo;

import java.util.List;

public interface ConvertService {

    List<PerformanceTrafficVo> convertTrafficEntityToVo(List<PerformanceAdnEntryTrafficEntity> trafficList, PerformanceQueryBean performanceQueryBean);

}
