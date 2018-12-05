package com.ccit.dao.impl;

import com.ccit.entity.OrderInternetCafeEntity;
import com.ccit.dao.OrderInternetCafeDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderInternetCafeDaoImpl extends BaseDaoImpl<OrderInternetCafeEntity, Long> implements OrderInternetCafeDao {

    @Override
    public List<OrderInternetCafeEntity> findAll(){
        String hql = "from OrderInternetCafeEntity order by id desc ";
        Query query = currentSession().createQuery(hql);
        return query.list();
    }

}
