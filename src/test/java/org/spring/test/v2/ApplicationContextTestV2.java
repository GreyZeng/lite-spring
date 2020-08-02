package org.spring.test.v2;

import org.junit.Test;
import org.spring.context.ApplicationContext;
import org.spring.context.support.ClassPathXmlApplicationContext;
import org.spring.service.v2.UserService;

import static org.junit.Assert.*;

/**
 * @author zenghui
 * 2020/8/2
 */
public class ApplicationContextTestV2 {
    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-v2.xml");
        UserService userService = (UserService) context.getBean("userService");
        assertNotNull(userService);

        assertNotNull(userService.getAccountDao());
        assertNotNull(userService.getItemDao());
        assertEquals("test", userService.getOwner());
        assertTrue(userService.isChecked());
        assertEquals(2, userService.getVersion());
    }
}
