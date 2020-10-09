package com.song.spring.registry;

/**
 * 提供对单例bean集合进行操作的接口定义
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    void addSingleton(String beanName, Object bean);
}
