package com.ccit.web.controller;

import com.ccit.bean.*;
import com.ccit.enums.PerformanceInterval;
import com.ccit.enums.TransportProtocolEnum;
import com.ccit.exception.ParameterException;
import com.ccit.model.CustomerErrorConstants;
import com.ccit.model.LocalError;
import com.ccit.model.Validator;
import com.ccit.util.IPv4Utils;
import com.ccit.util.StringUtils;
import com.ccit.bean.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseController {

    public static final long DAY_MS = 1000l * 3600 * 24;
    public static final long TRIPLE_DAY_MS = 1000l * 3600 * 24 * 3;
    public static final long WEEK_MS = 1000l * 3600 * 24 * 7;
    public static final long MONTH_MS = 1000l * 3600 * 24 * 30;
    public static final long QUARTER_MS = 1000l * 3600 * 24 * 90;
    public static final long HALF_YEAR_MS = 1000l * 3600 * 24 * 180;
    public static final long YEAR_MS = 1000l * 3600 * 24 * 360;

    protected final Class EXCEPTION_CLASS = ParameterException.class;

    protected PerformanceQueryBean verifyPerformanceQueryBean(PerformanceQueryBean performanceQueryBean) throws ParameterException {
        if (performanceQueryBean == null) {
            performanceQueryBean = new PerformanceQueryBean();
        }

        if (performanceQueryBean.getEndTime() == null) {
            performanceQueryBean.setEndTime(new Date().getTime());
        }

        if (performanceQueryBean.getBeginTime() == null) {
            performanceQueryBean.setBeginTime(performanceQueryBean.getEndTime().getTime() - DAY_MS);
        }

        if (performanceQueryBean.getBeginTime().getTime() > performanceQueryBean.getEndTime().getTime()) {
            throw new ParameterException(CustomerErrorConstants.BEGIN_TIME_ILLEGAL);
        }

        if (performanceQueryBean.getInterval() == 0) {
            long scopeTime = (performanceQueryBean.getEndTime().getTime() - performanceQueryBean.getBeginTime().getTime());

            if (scopeTime <= DAY_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.DAILY.getCode());
            } else if (scopeTime > DAY_MS && scopeTime <= TRIPLE_DAY_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.TRIPLE_DAY.getCode());
            } else if (scopeTime > TRIPLE_DAY_MS && scopeTime <= WEEK_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.WEEKLY.getCode());
            } else if (scopeTime > WEEK_MS && scopeTime <= MONTH_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.MONTHLY.getCode());
            } else if (scopeTime > MONTH_MS && scopeTime <= QUARTER_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.QUARTERLY.getCode());
            } else if (scopeTime > QUARTER_MS && scopeTime <= HALF_YEAR_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.SEMIANNUAL.getCode());
            } else if (scopeTime > HALF_YEAR_MS) {
                performanceQueryBean.setInterval(PerformanceInterval.ANNUAL.getCode());
            }
        } else if (PerformanceInterval.getByCode(performanceQueryBean.getInterval()) == null) {
            throw new ParameterException(CustomerErrorConstants.INTERVAL_ILLEGAL);
        }

        return performanceQueryBean;
    }

    // 客户
    public void verifyCustomerInternetCafe(CustomerInternetCafeBean bean) {
        Validator.notEmpty(bean.getName(), error(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_NAME_EMPTY));
        Validator.notEmpty(bean.getContact(), error(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_CONTACT_EMPTY));
        Validator.isTrue(StringUtils.isBlank(bean.getContactPhone()) || StringUtils.isPhone(bean.getContactPhone()) || StringUtils.isTel(bean.getContactPhone()), error(CustomerErrorConstants.CUSTOMER_INTERNET_CAFE_PHONE_ILLEGAL));
    }

    public void verifyCustomerEnterprise(CustomerEnterpriseBean bean) {
        Validator.notEmpty(bean.getName(), error(CustomerErrorConstants.CUSTOMER_ENTERPRISE_NAME_EMPTY));
        Validator.notEmpty(bean.getContact(), error(CustomerErrorConstants.CUSTOMER_ENTERPRISE_CONTACT_EMPTY));
        Validator.isTrue(StringUtils.isBlank(bean.getContactPhone()) || StringUtils.isPhone(bean.getContactPhone()) || StringUtils.isTel(bean.getContactPhone()), error(CustomerErrorConstants.CUSTOMER_ENTERPRISE_PHONE_ILLEGAL));
    }

    // 盒子设备
    public void verifyBoxInternetCafe(BoxInternetCafeBean bean) {
        Validator.notEmpty(bean.getManufacturer(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_MANUFACTURER_EMPTY));
        Validator.notEmpty(bean.getModel(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_MODEL_EMPTY));
        Validator.notEmpty(bean.getSn(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_SN_EMPTY));
        Validator.notNull(bean.getWanCount(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_WAN_COUNT_NULL));
        Validator.isTrue(bean.getWanCount() > 0, error(CustomerErrorConstants.BOX_INTERNET_CAFE_WAN_COUNT_LE_ZERO));
    }

    public void verifyBoxNetwork(BoxNetworkBean networkBean) {
        Validator.notEmpty(networkBean.getManufacturer(), error(CustomerErrorConstants.BOX_NETWORK_MANUFACTURER_EMPTY));
        Validator.notEmpty(networkBean.getModel(), error(CustomerErrorConstants.BOX_NETWORK_MODEL_EMPTY));
        Validator.notEmpty(networkBean.getSn(), error(CustomerErrorConstants.BOX_NETWORK_SN_EMPTY));
        Validator.notNull(networkBean.getWanCount(), error(CustomerErrorConstants.BOX_NETWORK_WAN_COUNT_NULL));
        Validator.isTrue(networkBean.getWanCount() > 0, error(CustomerErrorConstants.BOX_NETWORK_WAN_COUNT_LE_ZERO));
    }

    public void verifyBoxEnterprise(BoxEnterpriseBean bean) {
        Validator.notEmpty(bean.getManufacturer(), error(CustomerErrorConstants.BOX_ENTERPRISE_MANUFACTURER_EMPTY));
        Validator.notEmpty(bean.getModel(), error(CustomerErrorConstants.BOX_ENTERPRISE_MODEL_EMPTY));
        Validator.notEmpty(bean.getSn(), error(CustomerErrorConstants.BOX_ENTERPRISE_SN_EMPTY));
        Validator.notNull(bean.getWanCount(), error(CustomerErrorConstants.BOX_ENTERPRISE_WAN_COUNT_NULL));
        Validator.isTrue(bean.getWanCount() > 0, error(CustomerErrorConstants.BOX_ENTERPRISE_WAN_COUNT_LE_ZERO));
    }


    // 用户
    public void verifyUser(UserBean bean) {
        Validator.notNull(bean.getUserName(), error(CustomerErrorConstants.USER_NAME_EMPTY));
        Validator.notNull(bean.getRealName(), error(CustomerErrorConstants.USER_REAL_NAME_EMPTY));
        Validator.notNull(bean.getRole(), error(CustomerErrorConstants.USER_ROLE_EMPTY));
        Validator.notNull(bean.getUserPassword(), error(CustomerErrorConstants.USER_PASSWORD_EMPTY));
        Validator.notNull(bean.getUserPasswordConfirm(), error(CustomerErrorConstants.USER_PASSWORD_CONFIRM_EMPTY));
        Validator.isTrue(StringUtils.equals(bean.getUserPassword(), bean.getUserPasswordConfirm()), error(CustomerErrorConstants.USER_PASSWORD_NOT_EQUALS));
    }

    // 加速入口
    public void verifyEntryIPIP(final EntryIPIPBean bean) {
        Validator.notNull(bean.getName(), error(CustomerErrorConstants.ENTRY_IPIP_NAME_EMPTY));
        Validator.notNull(bean.getMasterIP(), error(CustomerErrorConstants.ENTRY_IPIP_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(bean.getMasterIP()), error(CustomerErrorConstants.ENTRY_IPIP_IP_ILLEGAL));
        Validator.notNull(bean.getSlaveIP(), error(CustomerErrorConstants.ENTRY_IPIP_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(bean.getSlaveIP()), error(CustomerErrorConstants.ENTRY_IPIP_IP_ILLEGAL));
    }

    public void verifyEntryNetwork(final EntryNetworkBean networkBean) {
        Validator.notNull(networkBean.getName(), error(CustomerErrorConstants.ENTRY_NETWORK_NAME_EMPTY));
        Validator.notEmpty(networkBean.getOperators1(), error(CustomerErrorConstants.ENTRY_NETWORK_OPERATORS1_EMPTY));
        Validator.notEmpty(networkBean.getOperators2(), error(CustomerErrorConstants.ENTRY_NETWORK_OPERATORS2_EMPTY));
        Validator.notNull(networkBean.getMasterIP(), error(CustomerErrorConstants.ENTRY_NETWORK_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(networkBean.getMasterIP()), error(CustomerErrorConstants.ENTRY_NETWORK_IP_ILLEGAL));
        Validator.notNull(networkBean.getSlaveIP(), error(CustomerErrorConstants.ENTRY_NETWORK_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(networkBean.getSlaveIP()), error(CustomerErrorConstants.ENTRY_NETWORK_IP_ILLEGAL));
    }

    public void verifyEntryEnterpriseL2TP(EntryEnterpriseL2TPBean l2TPBean){
        Validator.notNull(l2TPBean.getName(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_NAME_EMPTY));
        Validator.notNull(l2TPBean.getMasterIP(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(l2TPBean.getMasterIP()), error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_IP_ILLEGAL));
        Validator.notNull(l2TPBean.getSlaveIP(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(l2TPBean.getSlaveIP()), error(CustomerErrorConstants.ENTRY_ENTERPRISE_L2TP_IP_ILLEGAL));
    }

    public void verifyEntryEnterpriseSS(final EntryEnterpriseSSBean bean) {
        Validator.notNull(bean.getName(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_NAME_EMPTY));

        Validator.notNull(bean.getIp(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_IP_EMPTY));
        Validator.isTrue(StringUtils.isIpv4(bean.getIp()), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_IP_ILLEGAL));

        Validator.notNull(bean.getPort(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_PORT_NULL));
        Validator.isTrue(StringUtils.isPort(bean.getPort()), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_PORT_ILLEGAL));

        Validator.notNull(bean.getDns(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_DNS_EMPTY));
        Validator.isTrue(isPositiveDNS(bean.getDns()), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_DNS_ILLEGAL));

        Validator.notNull(bean.getPassword(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_SHADOWSOCKS_PASSWORD_EMPTY));
    }

    public void verifyEntryEnterpriseGroupSS(EntryEnterpriseSSGroupBean entryBean) {
        Validator.notNull(entryBean.getName(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_NAME_EMPTY));
        Validator.notNull(entryBean.getDescription(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_DESCRIPTION_EMPTY));
        Validator.notNull(entryBean.getCommonMasterId(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_COMMON_MASTER_EMPTY));
        Validator.notNull(entryBean.getCommonSlaveId(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_COMMON_SLAVE_EMPTY));
        Validator.notNull(entryBean.getSpecialMasterId(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_SPECIAL_MASTER_EMPTY));
        Validator.notNull(entryBean.getSpecialSlaveId(), error(CustomerErrorConstants.ENTRY_ENTERPRISE_GROUP_SS_SPECIAL_SLAVE_EMPTY));
    }

    private boolean isPositiveDNS(String dns){

        String[] strings = dns.split("#");
        if(strings.length == 1){
            return StringUtils.isIpv4(strings[0]);
        }else if(strings.length == 2){
            return StringUtils.isIpv4(strings[0]) &&  StringUtils.isPositiveInteger(strings[1]);
        }

        return false;
    }

    // 加速业务
    public void verifyAccelerationIPIP(BoxInternetCafeBean boxBean) {
        Validator.notNull(boxBean.getSpeedUp(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_BANDWIDTH_MAX_NULL));
        Validator.notNull(boxBean.getSpeedDown(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_BANDWIDTH_MAX_NULL));
        Validator.notEmpty(boxBean.getActiveEntryList(), error(CustomerErrorConstants.BOX_INTERNET_CAFE_ACTIVE_ENTRY_EMPTY));
    }

    public void verifyAccelerationNetwork(BoxNetworkBean networkBean) {
        Validator.notNull(networkBean.getAccessUserName(), error(CustomerErrorConstants.BOX_NETWORK_ACCESS_USERNAME_EMPTY));
        Validator.notNull(networkBean.getAccessPassword(), error(CustomerErrorConstants.BOX_NETWORK_ACCESS_PASSWORD_EMPTY));
        Validator.notNull(networkBean.getUsedEntry(), error(CustomerErrorConstants.BOX_NETWORK_USED_ENTRY_EMPTY));
        Validator.notEmpty(networkBean.getActiveEntryList(), error(CustomerErrorConstants.BOX_NETWORK_ACTIVE_ENTRY_EMPTY));
    }

    public void verifyAccelerationShadowsocks(BoxEnterpriseBean boxBean) {
        Validator.notNull(boxBean.getSpeedUp(), error(CustomerErrorConstants.BOX_ENTERPRISE_BANDWIDTH_MAX_NULL));
        Validator.notNull(boxBean.getSpeedDown(), error(CustomerErrorConstants.BOX_ENTERPRISE_BANDWIDTH_MAX_NULL));
        Validator.notEmpty(boxBean.getAdvancedEntryList(), error(CustomerErrorConstants.BOX_ENTERPRISE_ADVANCED_ENTRY_LIST_EMPTY));
        Validator.notEmpty(boxBean.getSimpleEntryList(), error(CustomerErrorConstants.BOX_ENTERPRISE_SIMPLE_ENTRY_LIST_EMPTY));
        Validator.notNull(boxBean.getAccessUserName(), error(CustomerErrorConstants.BOX_ENTERPRISE_ACCESS_USER_NAME_EMPTY));
        Validator.notNull(boxBean.getAccessPassword(), error(CustomerErrorConstants.BOX_ENTERPRISE_ACCESS_USER_PASSWORD_EMPTY));
    }


    public void verifyPaymentEnterprise(PaymentEnterpriseBean paymentEnterpriseBean) {
        Validator.notNull(paymentEnterpriseBean.getBillingDate(), error(CustomerErrorConstants.PAYMENT_ENTERPRISE_BILLING_DATE_NULL));
        Validator.notNull(paymentEnterpriseBean.getPaymentDate(), error(CustomerErrorConstants.PAYMENT_ENTERPRISE_PAYMENT_DATE_NULL));
        Validator.isTrue(StringUtils.isNotBlank(paymentEnterpriseBean.getOperator()), error(CustomerErrorConstants.PAYMENT_ENTERPRISE_OPERATOR_NULL));
        Validator.isTrue(StringUtils.isPositiveInteger(paymentEnterpriseBean.getAmount()), error(CustomerErrorConstants.PAYMENT_ENTERPRISE_AMOUNT_ILLEGAL));
    }

    public void verifyAccelerationTarget(AccelerationTargetBean accelerationTargetBean) {
        Validator.isTrue(StringUtils.isIpv4OrIpv4Segment(accelerationTargetBean.getCidr()), error(CustomerErrorConstants.ACCELERATION_TARGET_CIDR_ILLEGAL));
    }

    protected ParameterException error(LocalError localError) {
        return ParameterException.error(localError);
    }

    public void verfiyAccelerationCollectionEnterpriseBean(AccelerationCollectionEnterpriseBean collectionBean) {
        Validator.notNull(collectionBean.getCollectionName(), error(CustomerErrorConstants.ACCELERATION_COLLECTION_NAME_NULL));
        Validator.notNull(collectionBean.getType(), error(CustomerErrorConstants.ACCELERATION_COLLECTION_TYPE_NULL));
        Validator.notNull(collectionBean.getAccelerateMode(), error(CustomerErrorConstants.ACCELERATION_COLLECTION_MODE_NULL));
    }

    public void verfiyUpdateCollectionEnterpriseBean(AccelerationCollectionEnterpriseBean collectionBean){
        Validator.notNull(collectionBean.getCollectionName(), error(CustomerErrorConstants.ACCELERATION_COLLECTION_NAME_NULL));
    }

    public void verfiyAccelerationIpsetEnterpriseBean(AccelerationIpsetEnterpriseBean ipsetBean) {
        Validator.notNull(ipsetBean.getIpsetName(), error(CustomerErrorConstants.ACCELERATION_IPSET_NAME_NULL));
        Validator.notNull(ipsetBean.getAccelerateMode(), error(CustomerErrorConstants.ACCELERATION_IPSET_ACCELERATE_MODE_NULL));
        Validator.isTrue(StringUtils.isEnglish(ipsetBean.getIpsetName()), error(CustomerErrorConstants.ACCELERATION_IPSET_NAME_ILLEGAL));
        if (StringUtils.isBlank(ipsetBean.getTransportProtocol())) {
            ipsetBean.setTransportProtocol(TransportProtocolEnum.ALL.getCode());
        }
        Validator.isTrue(isPortTotal(ipsetBean.getPortTotal()), error(CustomerErrorConstants.ACCELERATION_IPSET_PORT_ILLEGAL));
    }

    public void verifyAccelerationCidrEnterpriseBean(AccelerationCidrEnterpriseBean cidrBean) {
        Validator.isTrue(StringUtils.isIpv4OrIpv4Segment(cidrBean.getCidr()), error(CustomerErrorConstants.ACCELERATION_CIDR_ENTERPRISE_ILLEGAL));

        String[] temp = cidrBean.getCidr().split("/");
        if (cidrBean.getCidr().split("/").length == 1) {
            cidrBean.setCidr(IPv4Utils.transferCIDR(temp[0], "24"));
        } else {
            cidrBean.setCidr(IPv4Utils.transferCIDR(temp[0], temp[1]));
        }

        if (StringUtils.isBlank(cidrBean.getTransportProtocol())) {
            cidrBean.setTransportProtocol(TransportProtocolEnum.ALL.getCode());
        }

        if (cidrBean.getTransportProtocol().equals(TransportProtocolEnum.TCP.getCode())
                || cidrBean.getTransportProtocol().equals(TransportProtocolEnum.UDP.getCode())
                ) {
            Validator.isTrue(cidrBean.getPortSingle() == 0 || StringUtils.isPort(cidrBean.getPortSingle()),
                    error(CustomerErrorConstants.ACCELERATION_CIDR_ENTERPRISE_START_PORT_ILLEGAL));
        }
    }

    public void verifyAccelerationDomainEnterpriseBean(AccelerationDomainEnterpriseBean domainBean) {
        Validator.isTrue(StringUtils.isHostname(domainBean.getDomain()), error(CustomerErrorConstants.ACCELERATION_DOMAIN_ENTERPRISE_ILLEGAL));

    }

    public List<AccelerationCidrEnterpriseBean> verifyImportCIDRs(String[] rows, long collectionId, long ipsetId) {
        List<AccelerationCidrEnterpriseBean> cidrEnterpriseBeanList = new LinkedList();

        for (int i = 0; i < rows.length; i++) {

            CustomerErrorConstants.CIDR_ENTERPRISE_ILLEGAL.setMessage("第" + (i + 1) + "行,格式非法");

            String[] infos = rows[i].split(" ");

            AccelerationCidrEnterpriseBean cidrEnterpriseBean = new AccelerationCidrEnterpriseBean();
            cidrEnterpriseBean.setCollectionId(collectionId);
            cidrEnterpriseBean.setIpsetId(ipsetId);
            cidrEnterpriseBean.setCidr(infos[0]);

            try {
                verifyAccelerationCidrEnterpriseBean(cidrEnterpriseBean);
            } catch (RuntimeException e) {
                throw error(CustomerErrorConstants.CIDR_ENTERPRISE_ILLEGAL);
            }

            cidrEnterpriseBeanList.add(cidrEnterpriseBean);
        }
        return cidrEnterpriseBeanList;
    }

    public List<AccelerationDomainEnterpriseBean> verifyImportDomains(String[] rows, long collectionId, long ipsetId) {
        List<AccelerationDomainEnterpriseBean> domainEnterpriseBeanList = new LinkedList();

        for (int i = 0; i < rows.length; i++) {
            CustomerErrorConstants.DOMAIN_ENTERPRISE_ILLEGAL.setMessage("第" + (i + 1) + "行,域名非法");

            AccelerationDomainEnterpriseBean domainEnterpriseBean = new AccelerationDomainEnterpriseBean();
            domainEnterpriseBean.setCollectionId(collectionId);
            domainEnterpriseBean.setIpsetId(ipsetId);
            domainEnterpriseBean.setDomain(rows[i]);

            try {
                verifyAccelerationDomainEnterpriseBean(domainEnterpriseBean);
            } catch (RuntimeException e) {
                throw error(CustomerErrorConstants.DOMAIN_ENTERPRISE_ILLEGAL);
            }

            domainEnterpriseBeanList.add(domainEnterpriseBean);
        }

        return domainEnterpriseBeanList;
    }

    public void verifyNetworkLink(NetworkLinkBean networkLinkBean) {
        Validator.notNull(networkLinkBean.getBoxIdLeft(), error(CustomerErrorConstants.NETWORK_LINK_BOX_ID_LEFT_NULL));
        Validator.notNull(networkLinkBean.getConnectIpLeft(), error(CustomerErrorConstants.NETWORK_LINK_CONNECT_IP_LEFT_NULL));
        Validator.notNull(networkLinkBean.getBoxIdRight(), error(CustomerErrorConstants.NETWORK_LINK_BOX_ID_RIGHT_NULL));
        Validator.notNull(networkLinkBean.getConnectIpRight(), error(CustomerErrorConstants.NETWORK_LINK_CONNECT_IP_RIGHT_NULL));
        Validator.notNull(networkLinkBean.getAlias(), error(CustomerErrorConstants.NETWORK_LINK_ALIAS_NULL));
        Validator.notNull(networkLinkBean.getUserId(), error(CustomerErrorConstants.NETWORK_LINK_USER_ID_NULL));
        Validator.notNull(networkLinkBean.getLatency(), error(CustomerErrorConstants.NETWORK_LINK_LATENCY_NULL));
        Validator.notNull(networkLinkBean.getLossRate(), error(CustomerErrorConstants.NETWORK_LINK_LOSS_RATE_NULL));
        Validator.notNull(networkLinkBean.getHeartbeatTime(), error(CustomerErrorConstants.NETWORK_LINK_HEARTBEAT_TIME_NULL));
        Validator.notNull(networkLinkBean.getStatus(), error(CustomerErrorConstants.NETWORK_LINK_STATUS_NULL));
        Validator.notNull(networkLinkBean.getEnable(), error(CustomerErrorConstants.NETWORK_LINK_ENABLE_NULL));
    }

    public void verifyLinkBeanList(List<NetworkLinkBean> linkBeanList) {
        Validator.notNull(linkBeanList, error(CustomerErrorConstants.NETWORK_LINK_LIST_NULL));
        linkBeanList.forEach(networkLinkBean -> {
            Validator.notNull(networkLinkBean.getAlias(), error(CustomerErrorConstants.NETWORK_LINK_ALIAS_NULL));
            Validator.notNull(networkLinkBean.getLatency(), error(CustomerErrorConstants.NETWORK_LINK_LATENCY_NULL));
            Validator.notNull(networkLinkBean.getLossRate(), error(CustomerErrorConstants.NETWORK_LINK_LOSS_RATE_NULL));
            Validator.notNull(networkLinkBean.getStatus(), error(CustomerErrorConstants.NETWORK_LINK_STATUS_NULL));
            Validator.notNull(networkLinkBean.getHeartbeatTime(), error(CustomerErrorConstants.NETWORK_LINK_HEARTBEAT_TIME_NULL));
        });
    }

    public AccelerationBoxUpgradeTaskBean verifyAccelerationBoxUpgradeTaskBean(AccelerationBoxUpgradeTaskBean accelerationBoxUpgradeTaskBean){
        Validator.notNull(accelerationBoxUpgradeTaskBean.getStartTime(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_START_TIME_NULL));
        Validator.notNull(accelerationBoxUpgradeTaskBean.getEndTime(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_END_TIME_NULL));
        Validator.notNull(accelerationBoxUpgradeTaskBean.getPackageId(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_PACKAGE_ID_NULL));
        Validator.notNull(accelerationBoxUpgradeTaskBean.getBoxIdList(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_TASK_BOX_ID_NULL));

        return accelerationBoxUpgradeTaskBean;
    }

    public NetworkBoxUpgradeTaskBean verifyNetworkBoxUpgradeTaskBean(NetworkBoxUpgradeTaskBean networkBoxUpgradeTaskBean){
        Validator.notNull(networkBoxUpgradeTaskBean.getStartTime(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_START_TIME_NULL));
        Validator.notNull(networkBoxUpgradeTaskBean.getEndTime(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_END_TIME_NULL));
        Validator.notNull(networkBoxUpgradeTaskBean.getPackageId(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_PACKAGE_ID_NULL));
        Validator.notNull(networkBoxUpgradeTaskBean.getBoxIdList(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_TASK_BOX_ID_NULL));

        return networkBoxUpgradeTaskBean;
    }

    public void verifyAccelerationBoxUpgradePackageBean(AccelerationBoxUpgradePackageBean packageBean){
        Validator.notNull(packageBean.getPackageVersion(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_VERSION_NULL));
        Validator.notNull(packageBean.getSuitableVersion(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_SUITABLE_VERSION_NULL));
        Validator.notNull(packageBean.getFileMd5(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_FILEMD5_NULL));
        Validator.notNull(packageBean.getDownloadUserName(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_DOWNLOAD_USERNAME_NULL));
        Validator.notNull(packageBean.getDownloadPassword(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_DOWNLOAD_PASSWORD_NULL));
    }

    public void verifyAccelerationBoxUpgradePackSuitBean(AccelerationBoxUpgradePackageBean packageBean){
        Validator.notNull(packageBean.getPackageVersion(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_VERSION_NULL));
        Validator.notNull(packageBean.getSuitableVersion(), error(CustomerErrorConstants.ACCELERATION_BOX_UPGRADE_PACKAGE_SUITABLE_VERSION_NULL));

    }

    public void verifyNetworkBoxUpgradePackageBean(NetworkBoxUpgradePackageBean packageBean){
        Validator.notNull(packageBean.getPackageVersion(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_VERSION_NULL));
        Validator.notNull(packageBean.getSuitableVersion(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_SUITABLE_VERSION_NULL));
        Validator.notNull(packageBean.getFileMd5(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_FILEMD5_NULL));
        Validator.notNull(packageBean.getDownloadUserName(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_DOWNLOAD_USERNAME_NULL));
        Validator.notNull(packageBean.getDownloadPassword(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_DOWNLOAD_PASSWORD_NULL));

    }

    public void verifyNetworkBoxUpgradePackSuitBean(NetworkBoxUpgradePackageBean packageBean){
        Validator.notNull(packageBean.getPackageVersion(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_VERSION_NULL));
        Validator.notNull(packageBean.getSuitableVersion(), error(CustomerErrorConstants.NETWORK_BOX_UPGRADE_PACKAGE_SUITABLE_VERSION_NULL));

    }

    public void verifyNetworkLinkBean(NetworkLinkBean networkLinkBean) {
        Validator.notNull(networkLinkBean.getBandwidth(), error(CustomerErrorConstants.NETWORK_LINK_BANDWIDTH_NULL));
    }

    public AdnEntryBean verifyAdnEntryBean(AdnEntryBean adnEntryBean) {
        Validator.notNull(adnEntryBean.getEntryName(), error(CustomerErrorConstants.ADN_ENTRY_ENTRY_NAME_NULL));
        Validator.notNull(adnEntryBean.getEntryType(), error(CustomerErrorConstants.ADN_ENTRY_ENTRY_TYPE_NULL));
//        Validator.notNull(adnEntryBean.getControlIp(), error(CustomerErrorConstants.ADN_ENTRY_CONTROL_IP_NULL));
//        Validator.isTrue(isIpv4(adnEntryBean.getControlIp()), error(CustomerErrorConstants.ADN_ENTRY_CONTROL_IP_ILLEGAL));
        Validator.notNull(adnEntryBean.getServerIp(), error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_NULL));
        Validator.isTrue(StringUtils.isIpv4(adnEntryBean.getServerIp()), error(CustomerErrorConstants.ADN_ENTRY_SERVER_IP_ILLEGAL));
//        Validator.notNull(adnEntryBean.getControlPort(), error(CustomerErrorConstants.ADN_ENTRY_CONTROL_PORT_NULL));
//        Validator.isTrue(isPort(adnEntryBean.getControlPort()), error(CustomerErrorConstants.ADN_ENTRY_CONTROL_PORT_ILLEGAL));
        Validator.notNull(adnEntryBean.getZoneId(), error(CustomerErrorConstants.ADN_ENTRY_ZONE_ID_NULL));

        return adnEntryBean;
    }

    public void verifyAdnZoneBean(AdnZoneBean adnZoneBean) {
        Validator.notNull(adnZoneBean.getZoneName(), error(CustomerErrorConstants.ADN_ZONE_NAME_NULL));
        Validator.notNull(adnZoneBean.getLongitude(), error(CustomerErrorConstants.ADN_ZONE_LONGITUDE_NULL));
        Validator.notNull(adnZoneBean.getLatitude(), error(CustomerErrorConstants.ADN_ZONE_LATITUDE_NULL));

    }

    public void verifyAdnCustomerDomainBean(AdnCustomerDomainBean adnCustomerDomainBean) {
        Validator.isTrue(StringUtils.isHostname(adnCustomerDomainBean.getSecondLevelDomain()), error(CustomerErrorConstants.ACCELERATION_DOMAIN_ENTERPRISE_ILLEGAL));

    }

    public boolean isPortTotal(String port) {

        if (StringUtils.isNull(port)) {
            return false;
        }

        boolean checkPass = false;
        String[] ports = port.split(",");
        Pattern pattern = Pattern.compile("^[1-9]+[0-9]*$");
        for (int i = 0; i < ports.length; i++) {
            String por = ports[i];
            if (por.split(":").length == 2) {
                Matcher matcher1 = pattern.matcher(por.split(":")[0]);
                Matcher matcher2 = pattern.matcher(por.split(":")[1]);
                if (matcher1.matches() && matcher2.matches()) {
                    checkPass = true;
                }
            } else {
                Matcher matcher = pattern.matcher(por);
                checkPass = matcher.matches();
            }
        }
        return checkPass;
    }

    public boolean isPositiveString(String target) {
        if (target == null) {
            return false;
        }

        Pattern pattern = Pattern.compile("^[a-z0-9]+$");
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }
}


