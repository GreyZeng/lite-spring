package org.spring.test.v4;

import org.junit.Test;
import org.spring.beans.factory.annotation.ClassPathBeanDefinitionScanner;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.dao.v4.AccountDao;
import org.spring.dao.v4.ItemDao;
import org.spring.service.v4.UserService;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * @author zenghui
 * 2020/8/4
 */
public class ClassPathBeanDefinitionScannerTest {
    @Test
    public void testParseScannerBean() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        String basePackages = "org.spring.service.v4,org.spring.dao.v4";
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        scanner.doScan(basePackages);
        UserService userService = (UserService)factory.getBean("userService");
        ItemDao itemDao = (ItemDao)factory.getBean("itemDao");
        AccountDao accountDao = (AccountDao)factory.getBean("accountDao");
        assertNotNull(userService);
        assertNotNull(accountDao);
        assertNotNull(itemDao);
    }
}
