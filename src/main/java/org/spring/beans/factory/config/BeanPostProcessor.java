package org.spring.beans.factory.config;

import org.spring.beans.BeansException;

/**
 * @author Grey
 * 2020/8/3
 */
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean, String beanName) throws BeansException;

    Object afterInitialization(Object bean, String beanName) throws BeansException;
}
