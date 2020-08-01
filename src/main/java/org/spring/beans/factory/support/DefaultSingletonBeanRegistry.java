package org.spring.beans.factory.support;

import org.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zenghui
 * 2020/8/1
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    // TODO 考虑线程安全的容器
    private final Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        // TODO 异常处理
        if (null == beanName || "".equals(beanName.trim())) {
            throw new RuntimeException("beanName null error");
        }
        Object oldObject = this.singletonObjects.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException("Could not register object [" + singletonObject +
                    "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
        }
        this.singletonObjects.put(beanName, singletonObject);
    }

    @Override
    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
