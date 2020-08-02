package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grey
 * 2020/7/31
 */
public class GenericBeanDefinition implements BeanDefinition {
    private String beanId;
    private String beanClassName;
    // 默认是单例
    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<>();

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
    }

    @Override
    public boolean isPrototype() {
        return SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String getBeanClassName() {
        return beanClassName;
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }
}
