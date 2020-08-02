package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.ConstructorArgument;
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
    private Class<?> beanClass;
    // 默认是单例
    private String scope = SCOPE_DEFAULT;
    private List<PropertyValue> propertyValues = new ArrayList<>();
    private ConstructorArgument constructorArgument = new ConstructorArgument();

    public GenericBeanDefinition() {
    }

    public GenericBeanDefinition(String beanId, String beanClassName) {
        this.beanId = beanId;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getID() {
        return this.beanId;
    }

    public void setId(String id) {
        this.beanId = id;
    }

    public void setBeanClassName(String className) {
        this.beanClassName = className;
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

    @Override
    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    @Override
    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }

    // TODO 注意ClassLoader
    @Override
    public Class<?> resolveBeanClass() throws ClassNotFoundException {
        String className = getBeanClassName();
        if (className == null) {
            return null;
        }
        Class<?> resolvedClass = Thread.currentThread().getContextClassLoader().loadClass(className);
        this.beanClass = resolvedClass;
        return resolvedClass;
    }

    @Override
    public Class<?> getBeanClass() throws IllegalStateException {
        if (this.beanClass == null) {
            throw new IllegalStateException(
                    "Bean class name [" + this.getBeanClassName() + "] has not been resolved into an actual Class");
        }
        return this.beanClass;
    }

    @Override
    public boolean hasBeanClass() {
        return this.beanClass != null;
    }
}
