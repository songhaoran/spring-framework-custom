package com.song.spring.registry;

import com.song.spring.ioc.BeanDefinition;

import java.util.List;

/**
 * 提供操作beanDefinition的功能接口
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String beanName);

    List<BeanDefinition> getBeanDefinitions();

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
