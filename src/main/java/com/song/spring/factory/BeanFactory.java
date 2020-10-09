package com.song.spring.factory;

/**
 * spring容器的顶级接口
 */
public interface BeanFactory {

    Object getBean(String beanName);
}
