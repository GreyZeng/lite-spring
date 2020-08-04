package org.spring.test.v4;

import org.junit.Before;
import org.junit.Test;
import org.spring.beans.factory.annotation.AutowiredFieldElement;
import org.spring.beans.factory.annotation.InjectionElement;
import org.spring.beans.factory.annotation.InjectionMetadata;
import org.spring.beans.factory.support.DefaultBeanFactory;
import org.spring.beans.factory.xml.XmlBeanDefinitionReader;
import org.spring.core.io.ClassPathResource;
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

    @Before
    public void setup() {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions(new ClassPathResource("bean-v4.xml"));
    }

    @Test
    public void testInjection() throws Exception {
        LinkedList<InjectionElement> elements = new LinkedList<>();
        {
            Field field = UserService.class.getDeclaredField("accountDao");
            InjectionElement injectionElem = new AutowiredFieldElement(field, true, factory);
            elements.add(injectionElem);
        }
        {
            Field field = UserService.class.getDeclaredField("itemDao");
            InjectionElement injectionElem = new AutowiredFieldElement(field, true, factory);
            elements.add(injectionElem);
        }

        InjectionMetadata metadata = new InjectionMetadata(elements);

        UserService userService = new UserService();

        metadata.inject(userService);

        assertNotNull(userService.getAccountDao());

        assertNotNull(userService.getItemDao());

    }
}
