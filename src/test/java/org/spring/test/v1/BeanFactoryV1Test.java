package org.spring.test.v1;

import org.junit.Before;
import org.junit.Test;
import org.spring.beans.factory.BeanFactory;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.FileSystemResource;
import org.spring.service.v1.UserService;

import static org.junit.Assert.assertNotNull;

/**
 * @author Grey
 * 2020/7/31
 */
public class BeanFactoryV1Test {
    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;

    @Before
    public void setup() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testGetBean() {
        reader.loadBeanDefinitions(new ClassPathResource("bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }

    @Test
    public void testGetBean2() {
        reader.loadBeanDefinitions(new FileSystemResource("src\\test\\resources\\bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }
}
