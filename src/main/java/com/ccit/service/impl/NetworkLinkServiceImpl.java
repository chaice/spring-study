package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.NetworkLinkBean;
import com.ccit.dao.BoxNetworkDao;
import com.ccit.dao.NetworkLinkDao;
import com.ccit.dao.CustomerEnterpriseDao;
import com.ccit.entity.BoxNetworkEntity;
import com.ccit.entity.NetworkLinkEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.rest.axis.interfaces.NetworkLinkIF;
import com.ccit.rest.axis.request.NetworkLinkReq;
import com.ccit.rest.axis.response.NetworkLinkRes;
import com.ccit.service.NetworkLinkService;
import com.ccit.vo.BoxNetworkVo;
import com.ccit.vo.NetworkLinkVo;
import com.ccit.vo.CustomerEnterpriseVo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class NetworkLinkServiceImpl implements NetworkLinkService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private BoxNetworkDao boxNetworkDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private NetworkLinkDao networkLinkDao;

    @Autowired
    private Assembler<NetworkLinkBean, NetworkLinkEntity, NetworkLinkVo> linkVoAssembler;

    @Autowired
    @Qualifier("networkLinkIF")
    private NetworkLinkIF linkIF;

    @Override
    public List<NetworkLinkVo> listNetworkLink() {
        List<NetworkLinkEntity> networkLinkEntityList = networkLinkDao.findAll();

        List<NetworkLinkVo> networkLinkVoList = new LinkedList<>();

        networkLinkEntityList.forEach(networkLinkEntity -> {
            NetworkLinkVo linkVo = linkVoAssembler.toVo(networkLinkEntity);
            linkVo = toVo(networkLinkEntity,linkVo);
            networkLinkVoList.add(linkVo);
        });

        return networkLinkVoList;
    }

    @Override
    public NetworkLinkVo getNetworkLink(long id) {
        NetworkLinkEntity linkEntity = networkLinkDao.findOne(id);
        Validator.notNull(linkEntity, ResourceException.error(CustomerErrorConstants.NETWORK_LINK_NOT_EXIST));

        NetworkLinkVo linkVo = linkVoAssembler.toVo(linkEntity);
        linkVo = toVo(linkEntity,linkVo);

        return linkVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void modifyPartLink(NetworkLinkBean networkLinkBean) {
       NetworkLinkEntity linkEntity = networkLinkDao.findOne(networkLinkBean.getId());
       Validator.notNull(linkEntity, ResourceException.error(CustomerErrorConstants.NETWORK_LINK_NOT_EXIST));

       linkEntity.setBandwidth(networkLinkBean.getBandwidth());

       networkLinkDao.saveOrUpdate(linkEntity);

       updateNetworkLinkOfAxis(linkEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncLink(NetworkLinkBean networkLinkBean) {
        NetworkLinkEntity linkEntity = networkLinkDao.findOne(networkLinkBean.getId());

        if(linkEntity == null){
            linkEntity = linkVoAssembler.fromBean(networkLinkBean);
        }else{
            linkEntity.setBoxIdLeft(networkLinkBean.getBoxIdLeft());
            linkEntity.setConnectIpLeft(networkLinkBean.getConnectIpLeft());
            linkEntity.setBoxIdRight(networkLinkBean.getBoxIdRight());
            linkEntity.setConnectIpRight(networkLinkBean.getConnectIpRight());
            linkEntity.setAlias(networkLinkBean.getAlias());
            linkEntity.setUserId(networkLinkBean.getUserId());
            linkEntity.setLatency(networkLinkBean.getLatency());
            linkEntity.setLossRate(networkLinkBean.getLossRate());
            linkEntity.setStatus(networkLinkBean.getStatus());
            linkEntity.setEnable(networkLinkBean.getEnable());
        }

        networkLinkDao.saveOrUpdate(linkEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void removeLink(long id) {
        NetworkLinkEntity linkEntity = networkLinkDao.findOne(id);
        if(linkEntity != null){
            networkLinkDao.deleteById(id);
            removeNetworkLinkOfAxis(id);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void syncLinksStatus(List<NetworkLinkBean> linkBeanList) {
        linkBeanList.forEach(networkLinkBean -> {
            NetworkLinkEntity linkEntity = networkLinkDao.findOne(networkLinkBean.getId());

            if(linkEntity != null){
                linkEntity.setAlias(networkLinkBean.getAlias());
                linkEntity.setLatency(networkLinkBean.getLatency());
                linkEntity.setLossRate(networkLinkBean.getLossRate());
                linkEntity.setCreateTime(networkLinkBean.getHeartbeatTime());
                linkEntity.setStatus(networkLinkBean.getStatus());
                networkLinkDao.saveOrUpdate(linkEntity);
            }
        });
    }

    private NetworkLinkVo toVo(NetworkLinkEntity networkEntity,NetworkLinkVo linkVo){
        BoxNetworkEntity leftNetworkEntity = boxNetworkDao.findOne(networkEntity.getBoxIdLeft());

        if(leftNetworkEntity != null){
            linkVo.setLeftBox(
                    new BoxNetworkVo(
                            leftNetworkEntity.getId(),
                            leftNetworkEntity.getAlias() == null ? leftNetworkEntity.getSerialNumber() : leftNetworkEntity.getAlias(),
                            leftNetworkEntity.getAlias()
                    )
            );
        }else {
            linkVo.setLeftBox(
                    new BoxNetworkVo(0l,"","")
            );
        }

        BoxNetworkEntity rightNetworkEntity = boxNetworkDao.findOne(networkEntity.getBoxIdRight());

        if(rightNetworkEntity != null){
            linkVo.setRightBox(
                    new BoxNetworkVo(
                            rightNetworkEntity.getId(),
                            rightNetworkEntity.getAlias() == null ? rightNetworkEntity.getSerialNumber() : rightNetworkEntity.getAlias(),
                            rightNetworkEntity.getAlias()
                    )
            );
        }else{
            linkVo.setRightBox(
                    new BoxNetworkVo(0l,"","")
            );
        }


        CustomerEnterpriseEntity customerEnterpriseEntity = null;
        if (null != networkEntity.getUserId()) {
            customerEnterpriseEntity = customerEnterpriseDao.findOne(networkEntity.getUserId());
        }

        if (customerEnterpriseEntity != null) {
            linkVo.setCustomer(new CustomerEnterpriseVo(customerEnterpriseEntity.getId(), customerEnterpriseEntity.getSextantName()));
        } else {
            linkVo.setCustomer(new CustomerEnterpriseVo(0l, ""));
        }

        return linkVo;
    }

    public void updateNetworkLinkOfAxis(NetworkLinkEntity linkEntity){
        NetworkLinkReq linkReq = new NetworkLinkReq();

        linkReq.setId(linkEntity.getId());
        linkReq.setBandwidth(linkEntity.getBandwidth());

        NetworkLinkRes linkRes = linkIF.updateBox(linkEntity.getId(), linkReq);
        if (!linkRes.isSuccess()) {
            logger.error(String.format("Axis-更新组网连接-同步失败，错误码：%s，错误信息：%s",
                    linkRes.getErrorBody().getErrorCode(),
                    linkRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.UPDATE_NETWORK_LINK_SYNCHRONIZATION_FAIL);
        }
    }

    public void removeNetworkLinkOfAxis(long id){
        NetworkLinkRes linkRes = linkIF.removeBox(id);
        if (!linkRes.isSuccess()) {
            logger.error(String.format("Axis-删除组网连接-同步失败，错误码：%s，错误信息：%s",
                    linkRes.getErrorBody().getErrorCode(),
                    linkRes.getErrorBody().getMessage()));
            throw new BusinessException(CustomerErrorConstants.DELETE_NETWORK_LINK_SYNCHRONIZATION_FAIL);
        }
    }
}
