package org.spring.test.v4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spring.beans.factory.config.DependencyDescriptor;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
import org.spring.dao.v4.AccountDao;
import org.spring.dao.v4.ItemDao;
import org.spring.service.v4.UserService;

import java.lang.reflect.Field;

/**
 * @author zenghui
 * 2020/8/2
 */
public class DependencyDescriptorTest {
    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;

    @Before
    public void setup() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("bean-v4.xml"));
    }

    @Test
    public void testResolveDependency() throws Exception {


        Field f = UserService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f, true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
        f = UserService.class.getDeclaredField("itemDao");
        descriptor = new DependencyDescriptor(f, true);
        o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof ItemDao);

    }
}
