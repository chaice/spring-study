package com.ccit.assembler;

import com.ccit.bean.NetworkLinkBean;
import com.ccit.entity.NetworkLinkEntity;
import com.ccit.model.Assembler;
import com.ccit.vo.NetworkLinkVo;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Component
public class NetworkLinkAssembler implements Assembler<NetworkLinkBean, NetworkLinkEntity, NetworkLinkVo> {

    @Override
    public NetworkLinkEntity fromBean(NetworkLinkBean bean) {
        NetworkLinkEntity networkEntity = new NetworkLinkEntity();

        networkEntity.setId(bean.getId());
        networkEntity.setBoxIdLeft(bean.getBoxIdLeft());
        networkEntity.setConnectIpLeft(bean.getConnectIpLeft());
        networkEntity.setBoxIdRight(bean.getBoxIdRight());
        networkEntity.setConnectIpRight(bean.getConnectIpRight());
        networkEntity.setAlias(bean.getAlias());
        networkEntity.setUserId(bean.getUserId());
        networkEntity.setBandwidth(bean.getBandwidth());
        networkEntity.setLatency(bean.getLatency());
        networkEntity.setLossRate(bean.getLossRate());
        networkEntity.setCreateTime(new Timestamp(System.currentTimeMillis()));
        networkEntity.setStatus(bean.getStatus());
        networkEntity.setEnable(bean.getEnable());

        return networkEntity;
    }

    @Override
    public NetworkLinkVo toVo(NetworkLinkEntity entity) {
        NetworkLinkVo networkVo = new NetworkLinkVo();
        networkVo.setId(entity.getId());
        networkVo.setLeftConnectIp(entity.getConnectIpLeft());
        networkVo.setRightConnectIp(entity.getConnectIpRight());
        networkVo.setAlias(entity.getAlias());
        networkVo.setBandwidth(entity.getBandwidth());
        networkVo.setLatency(entity.getLatency());
        networkVo.setLossRate(entity.getLossRate());
        networkVo.setStatus(entity.getStatus());
        networkVo.setEnable(entity.getEnable());

        return networkVo;
    }

    @Override
    public List<NetworkLinkVo> toVos(List<NetworkLinkEntity> entities) {
        List<NetworkLinkVo> voList = new LinkedList<>();
        entities.forEach(entity -> voList.add(toVo(entity)));
        return voList;
    }
}
