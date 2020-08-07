package org.spring.test.v5;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.spring.test.v4.*;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CGlibTest.class,
        PointcutTest.class,
        MethodLocatingFactoryTest.class

})
public class V5AllTest {
}
