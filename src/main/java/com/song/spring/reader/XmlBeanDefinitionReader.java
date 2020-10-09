package com.song.spring.reader;

import com.song.spring.registry.BeanDefinitionRegistry;
import com.song.spring.resource.Resource;
import com.song.spring.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * Created by Song on 2020/09/16.
 */
public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void loadBeanDefinitions(Resource resource) {
        InputStream inputStream = resource.getResource();
        this.doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) {
        Document document = DocumentUtils.getDocument(inputStream);
        DefaultBeanDefinitionDocumentReader reader = new DefaultBeanDefinitionDocumentReader(beanDefinitionRegistry);
        reader.registerBeanDefinition(document.getRootElement());
    }
}
