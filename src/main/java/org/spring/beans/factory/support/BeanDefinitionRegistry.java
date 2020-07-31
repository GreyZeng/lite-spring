package org.spring.beans.factory.support;


import org.spring.beans.BeanDefinition;

/**
 * @author zenghui
 * @date 2020/7/20
 */
public interface BeanDefinitionRegistry {


    /**
     * 注册Bean
     * @param beanId
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanId, BeanDefinition beanDefinition);
}
