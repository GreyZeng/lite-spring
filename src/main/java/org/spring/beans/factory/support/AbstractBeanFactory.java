package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author zenghui
 * 2020/8/9
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {
    protected abstract Object createBean(BeanDefinition bd) /*throws BeanCreationException*/;
}
