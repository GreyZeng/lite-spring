package org.spring.beans.factory;

import org.spring.beans.BeansException;

/**
 * @author zenghui
 * 2020/8/8
 */
public interface BeanFactoryAware {
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
