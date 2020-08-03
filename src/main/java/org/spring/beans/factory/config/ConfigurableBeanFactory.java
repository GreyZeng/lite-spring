package org.spring.beans.factory.config;

import java.util.List;

/**
 * @author Grey
 * 2020/8/3
 */
public interface ConfigurableBeanFactory  extends AutowireCapableBeanFactory {
    void addBeanPostProcessor(BeanPostProcessor postProcessor);

    List<BeanPostProcessor> getBeanPostProcessors();
}
