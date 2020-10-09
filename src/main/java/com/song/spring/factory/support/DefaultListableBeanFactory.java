package com.song.spring.factory.support;

import com.song.spring.factory.ListableBeanFactory;
import com.song.spring.ioc.BeanDefinition;
import com.song.spring.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2020/09/16.
 */
public class DefaultListableBeanFactory extends AbstractAutowireBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {

    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }
    
    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        List<BeanDefinition> results = new ArrayList<>();
        for (BeanDefinition bd : beanDefinitions.values()) {
            results.add(bd);
        }
        return results;
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
    }

    @Override
    public <T> List<T> getBeansByType(Class type) {
        List<T> results = new ArrayList<>();

        List<BeanDefinition> beanDefinitions = getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            // 判断 type 是否 是beanDefinition的父类
            if (type.isAssignableFrom(bd.getClazzType())) {
                results.add((T) getBean(bd.getBeanName()));
            }
        }
        return results;
    }

    @Override
    public List<String> getBeanNameByType(Class type) {
        return null;
    }
}
