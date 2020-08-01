package org.spring.beans.factory.config;

/**
 * @author zenghui
 * 2020/8/1
 */
public interface SingletonBeanRegistry {
    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
