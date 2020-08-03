package org.spring.context.support;

import org.spring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.spring.beans.factory.config.ConfigurableBeanFactory;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.context.ApplicationContext;
import org.spring.core.io.Resource;

/**
 * @author zenghui
 * 2020/8/1
 */
public abstract class AbstractApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    public AbstractApplicationContext(String configPath) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(getResourceByPath(configPath));
        registerBeanPostProcessors(factory);
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
    protected void registerBeanPostProcessors(ConfigurableBeanFactory beanFactory) {

        AutowiredAnnotationProcessor postProcessor = new AutowiredAnnotationProcessor();
        postProcessor.setBeanFactory(beanFactory);
        beanFactory.addBeanPostProcessor(postProcessor);

    }

    /**
     * 根据配置信息生成Resource
     *
     * @param path
     * @return
     */
    protected abstract Resource getResourceByPath(String path);
}
