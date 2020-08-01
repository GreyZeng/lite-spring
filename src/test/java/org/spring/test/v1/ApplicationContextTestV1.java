package org.spring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.spring.context.ApplicationContext;
import org.spring.context.support.ClassPathXmlApplicationContext;
import org.spring.context.support.FileSystemApplicationContext;
import org.spring.service.v1.UserService;

/**
 * @author zenghui
 * 2020/8/1
 */
public class ApplicationContextTestV1 {
    @Test
    public void testGetBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-v1.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        Assert.assertNotNull(userService);
    }

    @Test
    public void testGetBean2() {
        ApplicationContext ctx = new FileSystemApplicationContext("src\\test\\resources\\bean-v1.xml");
        UserService userService = (UserService) ctx.getBean("userService");
        Assert.assertNotNull(userService);
    }
}
