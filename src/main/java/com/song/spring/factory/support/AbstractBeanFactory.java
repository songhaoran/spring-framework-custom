package com.song.spring.factory.support;

import com.song.spring.factory.BeanFactory;
import com.song.spring.ioc.BeanDefinition;
import com.song.spring.registry.DefaultSingletonBeanRegistry;

/**
 * Created by Song on 2020/09/16.
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        // 1.先从Bean的map中查找,如果存在,返回
        Object singletonObject = getSingleton(beanName);
        if (singletonObject != null) {
            return singletonObject;
        }

        // 2. 不存在,则从Bean定义信息集合中查找BeanDefinition
        BeanDefinition beanDefinition = getBeanDefinition(beanName);
        if (beanDefinition == null) {
            return null;
        }

        // 3. 判断bean是单例还是多例
        // 4. 创建bean
        if (beanDefinition.isSingleton()) {
            singletonObject = this.createBean(beanDefinition);
            // 5. 创建完bean,放入bean的集合
            addSingleton(beanName, singletonObject);
        } else if (beanDefinition.isPrototype()) {
            singletonObject = this.createBean(beanDefinition);
        }
        return singletonObject;
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName);

    protected abstract Object createBean(BeanDefinition beanDefinition);
}
