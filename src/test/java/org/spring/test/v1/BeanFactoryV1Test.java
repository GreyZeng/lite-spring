package org.spring.test.v1;

import org.junit.Test;
import org.spring.beans.factory.BeanFactory;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.FileSystemResource;
import org.spring.service.v1.UserService;

import static org.junit.Assert.assertNotNull;

/**
 * @author Grey
 * 2020/7/31
 */
public class BeanFactoryV1Test {
    @Test
    public void testGetBean() {
        BeanFactory factory = new DefaultBeanFactory(new ClassPathResource("bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }
    @Test
    public void testGetBean2() {
        BeanFactory factory = new DefaultBeanFactory(new FileSystemResource("src\\test\\resources\\bean-v1.xml"));
        UserService userService = (UserService) factory.getBean("userService");
        assertNotNull(userService);
    }
}
