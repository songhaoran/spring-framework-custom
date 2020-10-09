package com.song.spring.reader;

import com.song.spring.ioc.BeanDefinition;
import com.song.spring.ioc.PropertyValue;
import com.song.spring.ioc.RuntimeBeanReference;
import com.song.spring.ioc.TypedStringValue;
import com.song.spring.registry.BeanDefinitionRegistry;
import org.dom4j.Element;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Song on 2020/09/21.
 */
public class DefaultBeanDefinitionDocumentReader implements BeanDefinitionDocumentReader{
    private BeanDefinitionRegistry registry;

    public DefaultBeanDefinitionDocumentReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void registerBeanDefinition(Element rootElement) {
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            // 获取标签名称
            String name = element.getName();
            if (name.equals("bean")) {
                // 解析默认标签，其实也就是bean标签
                parseDefaultElement(element);
            } else {
                // 解析自定义标签，比如aop:aspect标签
                parseCustomElement(element);
            }
        }
    }

    private void parseCustomElement(Element element) {

    }

    @SuppressWarnings("unchecked")
    private void parseDefaultElement(Element element) {
        try {
            String id = element.attributeValue("id");
            String name = element.attributeValue("name");
            String clazzName = element.attributeValue("class");
            String scope = element.attributeValue("scope");
            String initMethod = element.attributeValue("init-method");

            Class<?> clazzType = Class.forName(clazzName);

            String beanName = id == null || "".equals(id) ? name : id;
            beanName = beanName == null || "".equals(beanName) ? clazzType.getSimpleName() : beanName;
            scope = scope == null || "".equals(scope) ? BeanDefinition.SCOPE_SINGLETON : scope;

            BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
            beanDefinition.setScope(scope);
            beanDefinition.setInitMethod(initMethod);
            beanDefinition.setClazzType(clazzType);

            // property 属性
            List<Element> elements = element.elements();
            for (Element subElement : elements) {
                String subElementName = subElement.getName();
                if ("property".equals(subElementName)) {
                    parsePropertyValue(subElement, beanDefinition);
                } else {
                    // TODO: 2020/9/14
                }
            }

            // 注册bean
            this.registry.registerBeanDefinition(beanName, beanDefinition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parsePropertyValue(Element element, BeanDefinition beanDefinition) throws Exception {
        String name = element.attributeValue("name");
        String ref = element.attributeValue("ref");
        String value = element.attributeValue("value");

        if (value != null && "".equals(value) == false) {
            TypedStringValue typedStringValue = new TypedStringValue(value);
            Class clazzType = beanDefinition.getClazzType();
            Field field = clazzType.getDeclaredField(name);
            Class<?> fieldType = field.getType();
            typedStringValue.setTargetType(fieldType);

            PropertyValue propertyValue = new PropertyValue(name, typedStringValue);
            beanDefinition.addPropertyValue(propertyValue);
        } else if (ref != null && "".equals(ref) == false) {
            RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(ref);
            PropertyValue propertyValue = new PropertyValue(name, runtimeBeanReference);
            beanDefinition.addPropertyValue(propertyValue);
        } else {
            return;
        }
    }
}
