package org.spring.context.support;

import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.context.ApplicationContext;
import org.spring.core.io.FileSystemResource;

/**
 * @author zenghui
 * 2020/8/1
 */
public class FileSystemApplicationContext implements ApplicationContext {
    private final DefaultBeanFactory factory;

    public FileSystemApplicationContext(String configPath) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new FileSystemResource(configPath));
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
}
