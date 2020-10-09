package com.song.spring.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * 因为需要对单例bean集合进行严格的管理操作(解决线程安全问题),所以单独抽出一个类
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public void addSingleton(String beanName, Object bean) {
        // 这里可以使用双重校验
        this.singletonObjects.put(beanName, bean);
    }
}
