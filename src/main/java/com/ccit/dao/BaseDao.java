package com.ccit.dao;

import java.util.List;

public interface BaseDao<T, PK> {

    public void saveOrUpdate(T t);

    public void delete(T t);

    public void deleteById(PK id);

    public T findOne(PK id);

    public List<T> findAll();
}
