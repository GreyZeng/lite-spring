package org.spring.beans.factory;

/**
 * @author Grey
 * 2020/7/31
 */
public interface BeanFactory {
    /**
     * 通过BeanID获取对象实例
     *
     * @param beanId
     * @return
     */
    Object getBean(String beanId);
}
