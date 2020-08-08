package org.spring.beans.factory.support;


import org.spring.beans.BeanDefinition;

/**
 * @author zenghui
 * @date 2020/7/20
 */
public interface BeanDefinitionRegistry {


    BeanDefinition getBeanDefinition(String beanID);

    void registerBeanDefinition(String beanID, BeanDefinition bd);
}
