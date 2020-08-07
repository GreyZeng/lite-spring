package org.spring.test.v5;

import org.junit.Assert;
import org.junit.Test;
import org.spring.aop.config.MethodLocatingFactory;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.Resource;
import org.spring.tx.TransactionManager;

import java.lang.reflect.Method;

/**
 * @author zenghui
 * 2020/8/6
 */
public class MethodLocatingFactoryTest {
    @Test
    public void testGetMethod() throws Exception{
        DefaultBeanFactory beanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        Resource resource = new ClassPathResource("bean-v5.xml");
        reader.loadBeanDefinitions(resource);

        MethodLocatingFactory methodLocatingFactory = new MethodLocatingFactory();
        methodLocatingFactory.setTargetBeanName("tx");
        methodLocatingFactory.setMethodName("start");
        methodLocatingFactory.setBeanFactory(beanFactory);

        Method m = methodLocatingFactory.getObject();

        Assert.assertEquals(TransactionManager.class, m.getDeclaringClass());
        Assert.assertEquals(m, TransactionManager.class.getMethod("start"));

    }
}
