package org.spring.beans.factory;

import java.util.List;

/**
 * @author Grey
 * 2020/7/31
 */
public interface BeanFactory {
    Object getBean(String beanID);

    Class<?> getType(String name) /*throws NoSuchBeanDefinitionException*/;

    List<Object> getBeansByType(Class<?> type);
}
