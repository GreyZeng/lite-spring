package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.factory.BeanFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grey
 * 2020/7/31
 */
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry {
    /**
     * Key beanId
     * Value bean class 的全路径，例如：org.spring.service.v1.UserService
     */
    private static final Map<String, BeanDefinition> BEAN_MAP = new HashMap<>();

    public DefaultBeanFactory() {

    }

    @Override
    public Object getBean(String beanId) {
        // TODO bean存在与否判断
        // TODO 异常处理
        // TODO 构造函数带参数
        BeanDefinition definition = BEAN_MAP.get(beanId);
        Class target = null;
        try {
            target = Thread.currentThread().getContextClassLoader().loadClass(definition.getBeanClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return target.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        // TODO 判空
        return BEAN_MAP.get(beanId);
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        BEAN_MAP.put(beanId, beanDefinition);
    }
}
