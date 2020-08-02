package org.spring.beans;

import java.util.List;

/**
 * @author Grey
 * 2020/7/31
 */
public interface BeanDefinition {
    String SCOPE_DEFAULT = "";
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    String getID();

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();

    ConstructorArgument getConstructorArgument();

    boolean hasConstructorArgumentValues();

    boolean hasBeanClass();

    Class<?> getBeanClass() throws IllegalStateException;

    // TODO 注意ClassLoader
    Class<?> resolveBeanClass() throws ClassNotFoundException;
}
