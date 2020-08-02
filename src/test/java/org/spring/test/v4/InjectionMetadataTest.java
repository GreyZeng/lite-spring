package org.spring.test.v4;

import org.junit.Before;
import org.junit.Test;
import org.spring.beans.factory.annotation.AutowiredFieldElement;
import org.spring.beans.factory.annotation.InjectionElement;
import org.spring.beans.factory.annotation.InjectionMetadata;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
import org.spring.core.io.Resource;
import org.spring.service.v4.UserService;

import java.lang.reflect.Field;
import java.util.LinkedList;

import static org.junit.Assert.assertNotNull;

/**
 * @author zenghui
 * 2020/8/2
 */
public class InjectionMetadataTest {
    private DefaultBeanFactory factory;
    private XmlBeanDefinitionReader reader;

    @Before
    public void setup() {
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);
    }

    @Test
    public void testInjection() throws Exception {

        Resource resource = new ClassPathResource("bean-v4.xml");
        reader.loadBeanDefinitions(resource);

        Class<?> clz = UserService.class;
        LinkedList<InjectionElement> elements = new LinkedList<>();

        {
            Field f = UserService.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }
        {
            Field f = UserService.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutowiredFieldElement(f, true, factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(clz, elements);

        UserService userService = new UserService();

        metadata.inject(userService);

        assertNotNull(userService.getAccountDao());

        assertNotNull(userService.getItemDao());

    }
}
