package org.spring.context.support;

import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.context.ApplicationContext;
import org.spring.core.io.ClassPathResource;

/**
 * @author zenghui
 * 2020/8/1
 */
public class ClassPathXmlApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory;

    public ClassPathXmlApplicationContext(String configPath) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource(configPath));
    }

    @Override
    public Object getBean(String beanId) {
        return factory.getBean(beanId);
    }
}
