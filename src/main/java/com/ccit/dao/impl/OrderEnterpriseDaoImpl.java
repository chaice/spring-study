package com.ccit.dao.impl;

import com.ccit.entity.OrderEnterpriseEntity;
import com.ccit.enums.OrderEnterpriseStatus;
import com.ccit.util.StringUtils;
import com.ccit.dao.OrderEnterpriseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OrderEnterpriseDaoImpl extends BaseDaoImpl<OrderEnterpriseEntity, Long> implements OrderEnterpriseDao {

    @Override
    public List<OrderEnterpriseEntity> findAll(){
        String hql = "from OrderEnterpriseEntity order by id desc ";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }

    @Override
    public List<OrderEnterpriseEntity> listByProvinceAndEnterpriseName(String province,String enterpriseName) {

        String[] condition = new String[]{"WHERE orderStatus != :orderStatus","WHERE orderStatus = :orderStatus"};

        List<OrderEnterpriseEntity> orderEnterpriseEntityList = new LinkedList<>();

        for (int i = 0; i < condition.length; i++) {
            String hql = "from OrderEnterpriseEntity "+condition[i];
            if(!StringUtils.isNull(province)){
                hql += " AND province=:province ";
            }
            if(!StringUtils.isNull(enterpriseName)){
                hql += " AND enterpriseName LIKE :enterpriseName";
            }
            hql += " order by id desc";
            Query query = currentSession().createQuery(hql);

            query.setString("orderStatus", OrderEnterpriseStatus.EXPIRED.getName());

            if(!StringUtils.isNull(province)){
                query.setString("province",province);
            }
            if(!StringUtils.isNull(enterpriseName)){
                query.setString("enterpriseName","%"+enterpriseName+"%");
            }
            orderEnterpriseEntityList.addAll(query.list());
        }

        return orderEnterpriseEntityList;
    }
}
