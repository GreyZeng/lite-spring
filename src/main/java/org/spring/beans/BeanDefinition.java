package org.spring.beans;

/**
 * @author Grey
 * 2020/7/31
 */
public interface BeanDefinition {
    // TODO 可以用枚举优化
    String SCOPE_DEFAULT = "";
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    boolean isSingleton();

    boolean isPrototype();

    String getScope();

    void setScope(String scope);

    String getBeanClassName();
}
