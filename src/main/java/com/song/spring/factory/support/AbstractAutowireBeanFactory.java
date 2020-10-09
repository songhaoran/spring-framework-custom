package com.song.spring.factory.support;

import com.song.spring.aware.Aware;
import com.song.spring.aware.BeanFactoryAware;
import com.song.spring.init.InitializingBean;
import com.song.spring.ioc.BeanDefinition;
import com.song.spring.ioc.PropertyValue;
import com.song.spring.resolve.BeanDefinitionValueResolver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 真正实现bean创建
 */
public abstract class AbstractAutowireBeanFactory extends AbstractBeanFactory {


    @Override
    protected Object createBean(BeanDefinition beanDefinition) {
        try {
            // 4.1 bean的实例化
            Object bean = createBeanInsurance(beanDefinition);
            // 4.2 bean的属性填充
            populateBean(bean, beanDefinition);
            // 4.3 bean的初始化
            initializeBean(bean, beanDefinition);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private void initializeBean(Object bean, BeanDefinition beanDefinition) throws Exception {
        // 处理aware接口
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }

        // 处理InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterProperties();
        }

        invokeInitMethod(bean, beanDefinition);
    }

    private void invokeInitMethod(Object bean, BeanDefinition beanDefinition) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String initMethod = beanDefinition.getInitMethod();
        if (initMethod != null && "".equals(initMethod) == false) {
            Method method = beanDefinition.getClazzType().getDeclaredMethod(initMethod);
            method.invoke(bean);
        }
    }

    private Object createBeanInsurance(BeanDefinition beanDefinition) throws Exception {
        // 通过构造方法创建bean
        Class clazzType = beanDefinition.getClazzType();
        // 获取无参构造器
        Constructor constructor = clazzType.getDeclaredConstructor();
        Object instance = constructor.newInstance();
        return instance;
    }

    private void populateBean(Object bean, BeanDefinition beanDefinition) throws Exception {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        Class clazzType = beanDefinition.getClazzType();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();

            Object valueToUser = resolver.resolveValue(value);
            Field field = clazzType.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUser);
        }
    }
}
