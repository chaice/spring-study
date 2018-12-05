package com.ccit.model;

import java.util.List;

/**
 *  核心装配器
 *
 */
public interface Assembler<B, E, V> {

    /**
     * 将bean转换成entity
     * @param bean
     * @return
     */
    E fromBean(B bean);


    /**
     * 将entity转换成vo
     * @param entity
     * @return
     */
    V toVo(E entity);


    /**
     * entity列表转换成vo列表
     * @param entities
     * @return
     */
    List<V> toVos(List<E> entities);

}
