package org.spring.test.v4;

import org.junit.Test;
import org.spring.context.ApplicationContext;
import org.spring.context.support.ClassPathXmlApplicationContext;
import org.spring.service.v4.UserService;

import static org.junit.Assert.assertNotNull;

/**
 * @author zenghui
 * @Date 2020/7/27
 */
public class ApplicationContextTestV4 {
    @Test
    public void getBean() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-v4.xml");
        UserService userService = (UserService) ctx.getBean("userService");

        assertNotNull(userService.getAccountDao());
        assertNotNull(userService.getItemDao());
    }
}
