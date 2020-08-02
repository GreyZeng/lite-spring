package org.spring.beans.factory.config;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface AutowireCapableBeanFactory {
    /**
     * 将注入的类实例化
     *
     * @param descriptor
     * @return
     */
    Object resolveDependency(DependencyDescriptor descriptor);
}
