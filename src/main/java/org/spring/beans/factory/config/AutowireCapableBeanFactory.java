package org.spring.beans.factory.config;

import org.spring.beans.factory.BeanFactory;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    /**
     * 将注入的类实例化
     *
     * @param descriptor
     * @return
     */
    Object resolveDependency(DependencyDescriptor descriptor);
}
