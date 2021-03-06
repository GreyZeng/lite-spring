package org.spring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.spring.test.v4.*;

import java.sql.Ref;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApplicationContextTest5.class,
        BeanDefinitionTestV5.class,
        CGlibTest.class,
        PointcutTest.class,
        MethodLocatingFactoryTest.class,
        ReflectiveMethodInvocationTest.class,
        CglibAopProxyTest.class,
        BeanFactoryTestV5.class
})
public class V5AllTest {
}
