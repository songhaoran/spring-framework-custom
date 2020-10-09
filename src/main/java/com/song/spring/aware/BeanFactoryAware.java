package com.song.spring.aware;

import com.song.spring.factory.BeanFactory;

/**
 * Created by Song on 2020/09/30.
 */
public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
