package org.spring.context.support;

import org.spring.aop.aspectj.AspectJAutoProxyCreator;
import org.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.spring.beans.factory.config.ConfigurableBeanFactory;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.context.ApplicationContext;
import org.spring.core.io.Resource;

import java.util.List;

/**
 * @author zenghui
 * 2020/8/1
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private final DefaultBeanFactory factory;
    //private ClassLoader beanClassLoader;

    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = this.getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
        // factory.setBeanClassLoader(this.getBeanClassLoader());
        registerBeanPostProcessors(factory);
    }

    public Object getBean(String beanID) {

        return factory.getBean(beanID);
    }

    protected abstract Resource getResourceByPath(String path);

    /* public void setBeanClassLoader(ClassLoader beanClassLoader) {
         this.beanClassLoader = beanClassLoader;
     }

     public ClassLoader getBeanClassLoader() {
         return (this.beanClassLoader != null ? this.beanClassLoader : ClassUtils.getDefaultClassLoader());
     }*/
    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {
        {
            AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }
        {
            AspectJAutoProxyCreator postProcessor = new AspectJAutoProxyCreator();
            postProcessor.setBeanFactory(beanFactory);
            beanFactory.addBeanPostProcessor(postProcessor);
        }

    }

    public Class<?> getType(String name) {
        return this.factory.getType(name);
    }

    public List<Object> getBeansByType(Class<?> type) {
        return this.factory.getBeansByType(type);
    }
}
