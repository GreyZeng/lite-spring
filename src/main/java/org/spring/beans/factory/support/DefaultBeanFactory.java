package org.spring.beans.factory.support;

import org.spring.beans.BeanDefinition;
import org.spring.beans.BeansException;
import org.spring.beans.PropertyValue;
import org.spring.beans.SimpleTypeConverter;
import org.spring.beans.factory.BeanFactoryAware;
import org.spring.beans.factory.config.BeanPostProcessor;
import org.spring.beans.factory.config.DependencyDescriptor;
import org.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Grey
 * 2020/7/31
 */
public class DefaultBeanFactory extends AbstractBeanFactory implements BeanDefinitionRegistry {
    private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private final Map<String, BeanDefinition> BEAN_MAP = new ConcurrentHashMap<>(64);

    public DefaultBeanFactory() {

    }

    public void addBeanPostProcessor(BeanPostProcessor postProcessor) {
        this.beanPostProcessors.add(postProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.BEAN_MAP.put(beanID, bd);
    }

    public BeanDefinition getBeanDefinition(String beanID) {

        return this.BEAN_MAP.get(beanID);
    }

    public List<Object> getBeansByType(Class<?> type) {
        List<Object> result = new ArrayList<>();
        List<String> beanIDs = this.getBeanIDsByType(type);
        for (String beanID : beanIDs) {
            result.add(this.getBean(beanID));
        }
        return result;
    }

    private List<String> getBeanIDsByType(Class<?> type) {
        List<String> result = new ArrayList<>();
        for (String beanName : this.BEAN_MAP.keySet()) {
            if (type.isAssignableFrom(this.getType(beanName))) {
                result.add(beanName);
            }
        }
        return result;
    }

    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);
        if (bd == null) {
            return null;
        }

        if (bd.isSingleton()) {
            Object bean = this.getSingleton(beanID);
            if (bean == null) {
                bean = createBean(bd);
                this.registerSingleton(beanID, bean);
            }
            return bean;
        }
        return createBean(bd);
    }

    protected Object createBean(BeanDefinition bd) {
        //创建实例
        Object bean = instantiateBean(bd);
        //设置属性
        populateBean(bd, bean);

        bean = initializeBean(bd, bean);

        return bean;

    }

    private Object instantiateBean(BeanDefinition bd) {
        if (bd.hasConstructorArgumentValues()) {
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        } else {
            //ClassLoader cl = this.getBeanClassLoader();
            String beanClassName = bd.getBeanClassName();
            try {
                Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(beanClassName);
                return clz.newInstance();
            } catch (Exception e) {
                // TODO throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
                throw new RuntimeException("create bean for " + beanClassName + " failed", e);
            }
        }
    }

    protected void populateBean(BeanDefinition bd, Object bean) {

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
            // throw new BeanCreationException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
            throw new RuntimeException("Failed to obtain BeanInfo for class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    protected Object initializeBean(BeanDefinition bd, Object bean) {
        invokeAwareMethods(bean);
        //Todo，调用Bean的init方法，暂不实现
        if (!bd.isSynthetic()) {
            return applyBeanPostProcessorsAfterInitialization(bean, bd.getID());
        }
        return bean;
    }

    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeansException {

        Object result = existingBean;
        for (BeanPostProcessor beanProcessor : getBeanPostProcessors()) {
            result = beanProcessor.afterInitialization(result, beanName);
            if (result == null) {
                return null;
            }
        }
        return result;
    }

    private void invokeAwareMethods(final Object bean) {
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
    }


    public Object resolveDependency(DependencyDescriptor descriptor) {

        Class<?> typeToMatch = descriptor.getDependencyType();
        for (BeanDefinition bd : this.BEAN_MAP.values()) {
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
        if (!bd.hasBeanClass()) {
            try {
                bd.resolveBeanClass();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("can't load class:" + bd.getBeanClassName());
            }
        }
    }

    public Class<?> getType(String name) /*throws NoSuchBeanDefinitionException*/ {
        BeanDefinition bd = this.getBeanDefinition(name);
        if (bd == null) {
            // TODO throw new NoSuchBeanDefinitionException(name);
            throw new RuntimeException(name);
        }
        resolveBeanClass(bd);
        return bd.getBeanClass();
    }
}
