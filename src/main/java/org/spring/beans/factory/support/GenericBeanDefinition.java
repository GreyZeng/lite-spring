package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;

/**
 * @author Grey
 * 2020/7/31
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String beanId;
    private String beanClassName;

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return beanClassName;
    }
}
