package com.song.spring.reader;

import org.dom4j.Element;

/**
 * Created by Song on 2020/09/23.
 */
public interface BeanDefinitionDocumentReader {

    void registerBeanDefinition(Element rootElement);
}
