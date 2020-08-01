package org.spring.test.v1;

import org.junit.Before;
import org.junit.Test;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.FileSystemResource;
import org.spring.service.v1.OrgService;
import org.spring.service.v1.UserService;

import static org.junit.Assert.*;

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
    public void testGetBeanByClassPathResource() {
        reader.loadBeanDefinitions(new ClassPathResource("bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }

    @Test
    public void testGetBeanByFileSystemResource() {
        reader.loadBeanDefinitions(new FileSystemResource("src\\test\\resources\\bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }

    @Test
    public void testSingletonBean() {
        reader.loadBeanDefinitions(new ClassPathResource("bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
        UserService userService2 = (UserService) factory.getBean("userService");
        assertNotNull(userService2);
        assertEquals(userService, userService2);
    }

    @Test
    public void testGetPrototypeBean() {
        reader.loadBeanDefinitions(new FileSystemResource("src\\test\\resources\\bean-v1.xml"));
        OrgService orgService = (OrgService) factory.getBean("orgService");
        assertNotNull(orgService);
        OrgService orgService2 = (OrgService) factory.getBean("orgService");
        assertNotNull(orgService2);
        assertNotEquals(orgService, orgService2);
    }

}
