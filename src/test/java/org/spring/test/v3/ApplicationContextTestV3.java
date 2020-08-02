package org.spring.test.v3;

import org.junit.Test;
import org.spring.context.ApplicationContext;
import org.spring.context.support.ClassPathXmlApplicationContext;
import org.spring.service.v3.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author zenghui
 * 2020/8/2
 */
public class ApplicationContextTestV3 {
    @Test
    public void testGetBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-v3.xml");
        UserService userService = (UserService) context.getBean("userService");
        assertNotNull(userService);
        assertNotNull(userService.getAccountDao());
        assertNotNull(userService.getItemDao());
        // 另外一个构造方法中，version的值为-1
        assertEquals(1, userService.getVersion());
    }
}
