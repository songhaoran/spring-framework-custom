package com.song.spring.factory;

import java.util.List;

/**
 * 可列表话操作的Bean工厂
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 根据类型,获取该类型及所有子类的实例
     *
     * @param type
     * @param <T>
     * @return
     */
    <T> List<T> getBeansByType(Class type);

    /**
     * 根据类型,获取该类型及所有子类的实例的名称
     *
     * @param type
     * @return
     */
    List<String> getBeanNameByType(Class type);
}
