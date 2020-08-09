package org.spring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.spring.test.v1.V1AllTest;
import org.spring.test.v2.V2AllTest;
import org.spring.test.v3.V3AllTest;
import org.spring.test.v4.V4AllTest;
import org.spring.test.v5.V5AllTest;
import org.spring.test.v6.V6AllTest;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        V1AllTest.class,
        V2AllTest.class,
        V3AllTest.class,
        V5AllTest.class,
        V6AllTest.class,
        V4AllTest.class
})
public class AllTest {
}
