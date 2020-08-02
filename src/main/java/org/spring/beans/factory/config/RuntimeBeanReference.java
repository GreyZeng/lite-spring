package org.spring.beans.factory.config;

/**
 * @author zenghui
 * 2020/8/2
 */
public class RuntimeBeanReference {
    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
