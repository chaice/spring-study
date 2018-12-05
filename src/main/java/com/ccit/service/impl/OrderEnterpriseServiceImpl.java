package com.ccit.service.impl;

import com.ccit.exception.BusinessException;
import com.ccit.exception.ResourceException;
import com.ccit.bean.OrderCheckEnterpriseBean;
import com.ccit.dao.AgencyEnterpriseDao;
import com.ccit.dao.CustomerEnterpriseDao;
import com.ccit.dao.OrderEnterpriseDao;
import com.ccit.dao.PaymentEnterpriseDao;
import com.ccit.entity.AgencyEnterpriseEntity;
import com.ccit.entity.CustomerEnterpriseEntity;
import com.ccit.entity.OrderEnterpriseEntity;
import com.ccit.entity.PaymentEnterpriseEntity;
import com.ccit.enums.OrderEnterpriseStatus;
import com.ccit.enums.PaymentEnterpriseType;
import com.ccit.model.Assembler;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.Validator;
import com.ccit.service.OrderEnterpriseService;
import com.ccit.util.ExcelUtils;
import com.ccit.vo.OrderEnterpriseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class OrderEnterpriseServiceImpl implements OrderEnterpriseService
{

    @Autowired
    private OrderEnterpriseDao orderEnterpriseDao;

    @Autowired
    private CustomerEnterpriseDao customerEnterpriseDao;

    @Autowired
    private AgencyEnterpriseDao agencyEnterpriseDao;

    @Autowired
    private Assembler<OrderCheckEnterpriseBean, OrderEnterpriseEntity, OrderEnterpriseVo> orderEnterpriseAssembler;

    @Autowired
    private PaymentEnterpriseDao paymentEnterpriseDao;

    @Override
    public List<OrderEnterpriseVo> listOrder(String province,String collection,String enterpriseName) {
        List<OrderEnterpriseEntity> orderEntityList = orderEnterpriseDao.listByProvinceAndEnterpriseName(province,enterpriseName);

        List<OrderEnterpriseVo> orderVoList = new LinkedList<>();
        if (orderEntityList != null) {
            for (OrderEnterpriseEntity orderEntity : orderEntityList) {
                OrderEnterpriseVo orderVo = orderEnterpriseAssembler.toVo(orderEntity);

                CustomerEnterpriseEntity customerEntity = customerEnterpriseDao.findOne(orderEntity.getCustomerId());
                orderVo.setCustomerName(customerEntity != null ? customerEntity.getSextantName() : "");

                AgencyEnterpriseEntity retailerEntity = agencyEnterpriseDao.findOne(orderEntity.getRetailerId());
                orderVo.setRetailerName(retailerEntity != null ? retailerEntity.getName() : "");

                AgencyEnterpriseEntity wholesalerEntity = agencyEnterpriseDao.findOne(orderEntity.getWholesalerId());
                orderVo.setWholesalerName(wholesalerEntity != null ? wholesalerEntity.getName() : "");

                long collectionDay = 36500;
                if (orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.ACTIVATED.getName()) || orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.EXPIRED.getName())) {
                    PaymentEnterpriseEntity paymentEnterpriseEntity = paymentEnterpriseDao.findLastOneByOrderId(orderEntity.getId());
                    Timestamp lastBillingDate;
                    if (paymentEnterpriseEntity != null) {
                        lastBillingDate = paymentEnterpriseEntity.getBillingDate();
                    } else {
                        lastBillingDate = new Timestamp(orderEntity.getBillingTime().getTime() - TimeUnit.DAYS.toMillis(1));
                    }

                    if (orderEntity.getPaymentType().equals(PaymentEnterpriseType.PREPAID.getName())) {
                        collectionDay = (lastBillingDate.getTime() - System.currentTimeMillis()) / TimeUnit.DAYS.toMillis(1);
                    } else {
                        //TODO 后付的待处理
                    }
                }
                orderVo.setCollectionDay(collectionDay);
                if("expired_soon".equals(collection)){
                    if(collectionDay >= 0 && collectionDay <= 7){
                        orderVoList.add(orderVo);
                    }
                }else if("overdue".equals(collection)){
                    if(collectionDay < 0){
                        orderVoList.add(orderVo);
                    }
                }else{
                    orderVoList.add(orderVo);
                }

            }
        }

        return orderVoList;
    }

    @Override
    public OrderEnterpriseVo getOrder(long id) {
        OrderEnterpriseEntity orderEntity = orderEnterpriseDao.findOne(id);
        Validator.notNull(orderEntity, ResourceException.error(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_EXIST));

        OrderEnterpriseVo orderVo = orderEnterpriseAssembler.toVo(orderEntity);
        CustomerEnterpriseEntity customerEntity = customerEnterpriseDao.findOne(orderEntity.getCustomerId());
        orderVo.setCustomerName(customerEntity != null ? customerEntity.getSextantName() : "");

        AgencyEnterpriseEntity retailerEntity = agencyEnterpriseDao.findOne(orderEntity.getRetailerId());
        orderVo.setRetailerName(retailerEntity != null ? retailerEntity.getName() : "");

        AgencyEnterpriseEntity wholesalerEntity = agencyEnterpriseDao.findOne(orderEntity.getWholesalerId());
        orderVo.setWholesalerName(wholesalerEntity != null ? wholesalerEntity.getName() : "");

        return orderVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void checkOrder(OrderCheckEnterpriseBean orderCheckBean) {

        OrderEnterpriseEntity orderEntity = orderEnterpriseDao.findOne(orderCheckBean.getId());
        if (orderEntity == null) {
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_EXIST);
        }

        if (!orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.MANUFACTURER_CHECKING.getName())) {
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_IN_CHECKING_STATUS);
        }

        orderEntity.setOrderStatus(orderCheckBean.getOrderStatus());
        orderEntity.setWholesalerComment(orderCheckBean.getDisposeComment());
        orderEntity.setManufacturerTime(new Timestamp(System.currentTimeMillis()));
        orderEntity.setPaymentType(orderCheckBean.getPaymentType());
        orderEntity.setPaymentPeriod(orderCheckBean.getPaymentPeriod());

        if (orderCheckBean.getOrderStatus().equals(OrderEnterpriseStatus.ACTIVATED.getName())) {
            orderEntity.setOpenTime(orderCheckBean.getOpenTime());
            orderEntity.setBillingTime(orderCheckBean.getBillingTime());
            orderEntity.setCustomerId(orderCheckBean.getCustomerId());
        }

        orderEnterpriseDao.saveOrUpdate(orderEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updatePartOrder(OrderCheckEnterpriseBean orderCheckBean) {
        OrderEnterpriseEntity orderEntity = orderEnterpriseDao.findOne(orderCheckBean.getId());
        if (orderEntity == null) {
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_EXIST);
        }

        if (orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.ACTIVATED.getName())) {
            orderEntity.setOpenTime(orderCheckBean.getOpenTime()==null?orderEntity.getOpenTime():orderCheckBean.getOpenTime());
            orderEntity.setBillingTime(orderCheckBean.getBillingTime()==null?orderEntity.getBillingTime():orderCheckBean.getBillingTime());
            orderEntity.setEnterpriseName(orderCheckBean.getEnterpriseName()==null?orderEntity.getEnterpriseName():orderCheckBean.getEnterpriseName());
            orderEntity.setMachineNumber(orderCheckBean.getMachineNumber() == null?orderEntity.getMachineNumber():orderCheckBean.getMachineNumber());
        }else{
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_MODIFICATION);
        }

        orderEnterpriseDao.saveOrUpdate(orderEntity);

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void switchOrder(OrderCheckEnterpriseBean orderCheckBean){
        OrderEnterpriseEntity orderEntity = orderEnterpriseDao.findOne(orderCheckBean.getId());
        if (orderEntity == null) {
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_EXIST);
        }

        if (orderCheckBean.getOrderStatus().equals(OrderEnterpriseStatus.ACTIVATED.getName())) {
            if(orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.EXPIRED.getName())){
                orderEntity.setOrderStatus(orderCheckBean.getOrderStatus());
            }else{
                throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_OPEN);
            }

        } else if(orderCheckBean.getOrderStatus().equals(OrderEnterpriseStatus.EXPIRED.getName())){
            if(orderEntity.getOrderStatus().equals(OrderEnterpriseStatus.ACTIVATED.getName())){
                orderEntity.setOrderStatus(orderCheckBean.getOrderStatus());
            }else{
                throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_CLOSE);
            }

        }else{
            throw new BusinessException(CustomerErrorConstants.ORDER_ENTERPRISE_NOT_MODIFICATION);
        }

        orderEnterpriseDao.saveOrUpdate(orderEntity);


    }

    @Override
    public void exportOrder(HttpServletResponse response, String province, String collection,String enterpriseName) {
        List<OrderEnterpriseVo> orderEnterpriseVoList = listOrder(province,collection,enterpriseName);

        String[] headItem = new String[]{"编号","状态","一级","二级","网吧名称","地理位置","联系人","电话号码",
                "代理商","我方","开通时间","计费时间","服务带宽","网吧账号","付款方式","付款周期","结算日"};

        List<Map<Integer,String>> listData = new LinkedList<>();

        for (OrderEnterpriseVo orderEnterpriseVo :orderEnterpriseVoList) {
            Map<Integer,String> map = new Hashtable<>();
            //编号
            map.put(0,orderEnterpriseVo.getOrderId());
            //状态
            map.put(1,getOrderStatus(orderEnterpriseVo.getOrderStatus()));
            //一级
            map.put(2,orderEnterpriseVo.getWholesalerName());
            //二级
            map.put(3,orderEnterpriseVo.getRetailerName());
            //网吧名称
            map.put(4,orderEnterpriseVo.getEnterpriseName());
            //地理位置
            map.put(5,orderEnterpriseVo.getProvince()+orderEnterpriseVo.getCity()+(orderEnterpriseVo.getAddress() == null?"":orderEnterpriseVo.getAddress()));
            //联系人
            map.put(6,orderEnterpriseVo.getContact());
            //电话号码
            map.put(7,orderEnterpriseVo.getContactPhone());
            //代理商
            map.put(8,orderEnterpriseVo.getWholesalerTime());
            //我方
            map.put(9,orderEnterpriseVo.getManufacturerTime());
            //开通时间
            map.put(10,orderEnterpriseVo.getOpenTime());
            //计费时间
            map.put(11,orderEnterpriseVo.getBillingTime());
            //服务带宽
            map.put(12,orderEnterpriseVo.getBandwidth()+" mbps");
            if("activated".equals(orderEnterpriseVo.getOrderStatus())){
                //网吧账号
                map.put(13,orderEnterpriseVo.getCustomerName());
                //付款方式
                map.put(14,orderEnterpriseVo.getPaymentType()== "prepaid" ? "预付" : "后付");
                //付款周期
                map.put(15,orderEnterpriseVo.getPaymentPeriod()+"个月");
                //结算日
                map.put(16,orderEnterpriseVo.getCollectionDay() >= 0 ?"距离结算日还有 "+orderEnterpriseVo.getCollectionDay()+" 天":"已逾期"+Math.abs(orderEnterpriseVo.getCollectionDay())+" 天");
            }
            listData.add(map);
        }

        ExcelUtils excelUtils = new ExcelUtils();
        try {
            excelUtils.exportExcel(response,headItem,listData,"企业订单列表.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOrderStatus(String orderStatus){
        String status = "";
        if ("activated".equals(orderStatus)) {
            status = "已通过";
        } else if ("wholesaler_rejected".equals(orderStatus)) {
            status = "代理商驳回";
        } else if ("wholesaler_checking".equals(orderStatus)) {
            status = "代理商审核中";
        } else if ("manufacturer_rejected".equals(orderStatus)) {
            status = "我方驳回";
        } else if ("manufacturer_checking".equals(orderStatus)) {
            status = "我方审核中";
        }
        return status;
    }

}
