package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.PropertyValue;
import org.spring.beans.SimpleTypeConverter;
import org.spring.beans.factory.BeanFactory;
import org.spring.beans.factory.config.BeanPostProcessor;
import org.spring.beans.factory.config.ConfigurableBeanFactory;
import org.spring.beans.factory.config.DependencyDescriptor;
import org.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Grey
 * 2020/7/31
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry, ConfigurableBeanFactory {
    /**
     * TODO     考虑线程安全的容器
     * Key beanId
     * Value bean class 的全路径，例如：org.spring.service.v1.UserService
     */
    private static final Map<String, BeanDefinition> BEAN_MAP = new HashMap<>();
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public DefaultBeanFactory() {

    }

    @Override
    public Object getBean(String beanId) {
        // TODO bean存在与否判断
        // TODO 异常处理
        // TODO 构造函数带参数
        BeanDefinition definition = BEAN_MAP.get(beanId);
        if (definition.isSingleton()) {
            Object bean = this.getSingleton(beanId);
            if (bean == null) {
                bean = createBean(definition);
                this.registerSingleton(beanId, bean);
            }
            return bean;
        }
        return createBean(definition);
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Object bean = instantiateBean(beanDefinition);
        // 如果有必要的话，将ref bean的定义注入beanDefinition中
        populateBean(beanDefinition, bean);
        return bean;
    }

    private Object instantiateBean(BeanDefinition definition) {
        if (definition.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(definition);
        } else {
            Class target = null;
            try {
                target = Thread.currentThread().getContextClassLoader().loadClass(definition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {

                return target.newInstance();

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    private void populateBean(BeanDefinition bd, Object bean) {
        for (BeanPostProcessor processor : this.getBeanPostProcessors()) {
            if (processor instanceof InstantiationAwareBeanPostProcessor) {
                ((InstantiationAwareBeanPostProcessor) processor).postProcessPropertyValues(bean, bd.getID());
            }
        }
        List<PropertyValue> pvs = bd.getPropertyValues();
        if (pvs == null || pvs.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor pd : pds) {
                    if (pd.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());

                        pd.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            // TODO 封装Exception
            throw new RuntimeException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    @Override
    public void registerBeanDefinition(String beanId, BeanDefinition beanDefinition) {
        BEAN_MAP.put(beanId, beanDefinition);
    }

    @Override
    public Object resolveDependency(DependencyDescriptor descriptor) {

        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : BEAN_MAP.values()) {
            //确保BeanDefinition 有Class对象
            resolveBeanClass(bd);
            Class<?> beanClass = bd.getBeanClass();
            if (typeToMatch.isAssignableFrom(beanClass)) {
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    public void resolveBeanClass(BeanDefinition bd) {
        if (bd.hasBeanClass()) {
            return;
        } else {
            try {
                bd.resolveBeanClass();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    @Override
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
