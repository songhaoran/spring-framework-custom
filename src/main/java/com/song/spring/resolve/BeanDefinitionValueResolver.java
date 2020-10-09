package com.song.spring.resolve;

import com.song.spring.factory.BeanFactory;
import com.song.spring.ioc.RuntimeBeanReference;
import com.song.spring.ioc.TypedStringValue;

/**
 * Created by Song on 2020/09/16.
 */
public class BeanDefinitionValueResolver {

    private BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValue(Object object){
        if (object instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) object;
            Class<?> targetType = typedStringValue.getTargetType();
            String value = typedStringValue.getValue();
            return handleBaseType(targetType, value);
        } else if (object instanceof RuntimeBeanReference) {
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) object;
            String ref = runtimeBeanReference.getRef();

            // TODO: 2020/9/14 这里有可能会出现循环依赖
            return beanFactory.getBean(ref);
        }
        return null;
    }

    private Object handleBaseType(Class<?> clazz, String string) {
        if (clazz == Integer.class) {
            return Integer.parseInt(string);
        } else if (clazz == String.class) {
            return string;
        } else {
            // ...
        }
        return null;
    }
}
