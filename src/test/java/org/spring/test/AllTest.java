package org.spring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.spring.test.v1.V1AllTest;
import org.spring.test.v2.V2AllTest;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTest.class, V2AllTest.class})
public class AllTest {
}
