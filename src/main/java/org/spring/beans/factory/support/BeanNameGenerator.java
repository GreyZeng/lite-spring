package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;

/**
 * @author zenghui
 * 2020/8/2
 */
public interface BeanNameGenerator {

    /**
     * Generate a bean name for the given bean definition.
     *
     * @param definition the bean definition to generate a name for
     * @param registry   the bean definition registry that the given definition
     *                   is supposed to be registered with
     * @return the generated bean name
     */
    String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry);

}