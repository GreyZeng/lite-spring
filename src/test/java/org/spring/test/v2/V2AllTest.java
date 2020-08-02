package org.spring.test.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author zenghui
 * 2020/8/2
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class, CustomBooleanEditorTest.class, CustomNumberEditorTest.class, TypeConvertTest.class})
public class V2AllTest {
}