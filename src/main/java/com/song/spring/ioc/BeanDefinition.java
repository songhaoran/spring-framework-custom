package com.song.spring.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2020/09/14.
 */
public class BeanDefinition {

    // bean标签class属性
    private String clazzName;

    private Class clazzType;

    // bean标签的id和name属性值都会存到这, id和name是二选一的
    private String beanName;

    // 初始化方法
    private String initMethod;

    // 默认singleton 单例
    private String scope;

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    public BeanDefinition(String clazzName, String beanName) {
        this.clazzName = clazzName;
        this.beanName = beanName;
        this.clazzType = resolveClassName(clazzName);
    }

    private Class<?> resolveClassName(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public Class getClazzType() {
        return clazzType;
    }

    public void setClazzType(Class clazzType) {
        this.clazzType = clazzType;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValue(PropertyValue propertyValue) {
        this.propertyValues.add(propertyValue);
    }

    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(this.scope);
    }

    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(this.scope);
    }
}
