package org.spring.beans;

import java.util.List;

/**
 * @author Grey
 * 2020/7/31
 */
public interface BeanDefinition {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";
    String SCOPE_DEFAULT = "";

    boolean isSingleton();
    boolean isPrototype();
    String getScope();
    void setScope(String scope);

    String getBeanClassName();

    List<PropertyValue> getPropertyValues();
    ConstructorArgument getConstructorArgument();
    String getID();
    boolean hasConstructorArgumentValues();

    Class<?> resolveBeanClass() throws ClassNotFoundException;
    Class<?> getBeanClass() throws IllegalStateException ;
    boolean hasBeanClass();
    boolean isSynthetic();
}
